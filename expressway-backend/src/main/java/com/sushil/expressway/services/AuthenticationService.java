package com.sushil.expressway.services;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.sushil.expressway.entitys.Role;
import com.sushil.expressway.entitys.Token;
import com.sushil.expressway.entitys.User;
import com.sushil.expressway.models.AuthenticationRequest;
import com.sushil.expressway.models.AuthenticationResponse;
import com.sushil.expressway.models.RegistrationRequest;
import com.sushil.expressway.repositories.RoleRepository;
import com.sushil.expressway.repositories.TokenRepository;
import com.sushil.expressway.repositories.UserRepository;
import com.sushil.expressway.repositories.RefreshTokenRepository;
import com.sushil.expressway.entitys.RefreshToken;
import com.sushil.expressway.models.RefreshTokenResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final TokenRepository tokenRepository;
    private final RefreshTokenRepository refreshTokenRepository;

    @Transactional
    public Integer registerUser(RegistrationRequest request) {
        Role userRole = roleRepository.findByName("USER")
                .orElseThrow(() -> new RuntimeException("Role USER not found"));
        
        User user = User.builder()
        .firstName(request.getFirstName())
        .lastName(request.getLastName())
        .email(request.getEmail().toLowerCase())
        .password(passwordEncoder.encode(request.getPassword()))
        .roles(List.of(userRole))
        .build();
        User savedUser = userRepository.save(user);
        Integer token = generateActivationToken(savedUser);
        return token;
    }
    
    @Transactional
    private Integer generateActivationToken(User savedUser) {
        int newToken = generateToken(6);
        Token token = Token.builder()
                .token(String.valueOf(newToken))
                .user(savedUser)
                .expiresAt(LocalDateTime.now().plusMinutes(4)) // Token valid for 1 day
                .build();
        tokenRepository.save(token);
        return newToken;
    }

    private int generateToken(int tokenLength) {
        
        String digits = "0123456789";
        StringBuilder token = new StringBuilder();
        SecureRandom randomIndex = new SecureRandom();

        for (int i = 0; i < tokenLength; i++) {
            int index = randomIndex.nextInt(digits.length());
            token.append(digits.charAt(index));
        }
        return Integer.parseInt(token.toString());   
    }

    public Integer activateToken(String token) {

        Token savedToken = tokenRepository.findByToken(token)
            .orElseThrow(() -> new RuntimeException("Token Not Found!"));
        
        if(LocalDateTime.now().isAfter(savedToken.getExpiresAt())){
            return generateActivationToken(savedToken.getUser());
        }

        User user = userRepository.findById((savedToken.getUser().getId())).orElseThrow(() -> new RuntimeException("User not found"));

        user.setEnabled(true);
        userRepository.save(user);
        savedToken.setValidatedAt(LocalDateTime.now());
        tokenRepository.save(savedToken);

        return savedToken.getUser().getId().intValue();
    }

    @Transactional
    public AuthenticationResponse login(AuthenticationRequest request, HttpServletResponse response) {
        var auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

        var claims = new HashMap<String, Object>();
        User user = ((User) auth.getPrincipal());
        claims.put("fullName", user.fullName());

        var accessToken = jwtService.generateToken(claims, user);
        var refreshToken = jwtService.generateRefreshToken(user);
        
        saveRefreshToken(user, refreshToken);
        addRefreshTokenCookie(response, refreshToken);
        
        return AuthenticationResponse.builder()
                .accessToken(accessToken)
                .message("Login successful")
                .user(user)
                .build();
    }

    @Transactional
    public RefreshTokenResponse refreshToken(HttpServletRequest request) {
        String refreshToken = extractRefreshTokenFromCookie(request);
        if (refreshToken == null) {
            throw new RuntimeException("Refresh token not found");
        }

        String username = jwtService.extractUserName(refreshToken);
        if (username != null) {
            User user = userRepository.findByEmail(username)
                    .orElseThrow(() -> new RuntimeException("User not found"));
            
            RefreshToken storedToken = refreshTokenRepository.findByToken(refreshToken)
                    .orElseThrow(() -> new RuntimeException("Invalid refresh token"));
            
            if (storedToken.isRevoked() || storedToken.isExpired()) {
                throw new RuntimeException("Refresh token is invalid or expired");
            }
            
            if (jwtService.isTokenValid(refreshToken, user)) {
                var claims = new HashMap<String, Object>();
                claims.put("fullName", user.fullName());
                var accessToken = jwtService.generateToken(claims, user);
                
                return RefreshTokenResponse.builder()
                        .accessToken(accessToken)
                        .message("Token refreshed successfully")
                        .build();
            }
        }
        throw new RuntimeException("Invalid refresh token");
    }

    @Transactional
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        String refreshToken = extractRefreshTokenFromCookie(request);
        if (refreshToken != null) {
            refreshTokenRepository.findByToken(refreshToken)
                    .ifPresent(token -> {
                        token.setRevoked(true);
                        refreshTokenRepository.save(token);
                    });
        }
        clearRefreshTokenCookie(response);
    }

    private void saveRefreshToken(User user, String refreshToken) {
        refreshTokenRepository.revokeAllUserTokens(user);
        
        RefreshToken token = RefreshToken.builder()
                .token(refreshToken)
                .user(user)
                .expiresAt(LocalDateTime.now().plusDays(7))
                .revoked(false)
                .build();
        refreshTokenRepository.save(token);
    }

    private void addRefreshTokenCookie(HttpServletResponse response, String refreshToken) {
        Cookie cookie = new Cookie("refreshToken", refreshToken);
        cookie.setHttpOnly(true);
        cookie.setSecure(false); // Set to true in production with HTTPS
        cookie.setPath("/");
        cookie.setMaxAge(7 * 24 * 60 * 60); // 7 days
        response.addCookie(cookie);
    }

    private String extractRefreshTokenFromCookie(HttpServletRequest request) {
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if ("refreshToken".equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    private void clearRefreshTokenCookie(HttpServletResponse response) {
        Cookie cookie = new Cookie("refreshToken", "");
        cookie.setHttpOnly(true);
        cookie.setSecure(false);
        cookie.setPath("/");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }
}
