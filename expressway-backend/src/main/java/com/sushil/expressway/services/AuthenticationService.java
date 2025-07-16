package com.sushil.expressway.services;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.HashMap;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.sushil.expressway.entitys.Token;
import com.sushil.expressway.entitys.User;
import com.sushil.expressway.models.AuthenticationRequest;
import com.sushil.expressway.models.AuthenticationResponse;
import com.sushil.expressway.models.RegistrationRequest;
import com.sushil.expressway.repositories.TokenRepository;
import com.sushil.expressway.repositories.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final TokenRepository tokenRepository;


    public Integer registerUser(RegistrationRequest request) {

        User user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();
        User savedUser = userRepository.save(user);
        Integer token = generateActivationToken(savedUser);
        return token;
    }

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

    public AuthenticationResponse login(AuthenticationRequest request) {

        var auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

        var claims = new HashMap<String, Object>();
        User user = ((User) auth.getPrincipal());
        claims.put("fullName", user.fullName());

        var jwt = jwtService.generateToken(claims, user);
        return AuthenticationResponse.builder().token(jwt).build();
    }
}
