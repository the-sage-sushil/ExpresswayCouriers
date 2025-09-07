import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { Observable, tap, BehaviorSubject } from 'rxjs';
import { AuthenticationRequest, AuthenticationResponse, RegistrationRequest, RefreshTokenResponse } from './models/authentication.model';
import { environment } from '../../environments/environment';
import { TokenService } from './token.service';

@Injectable({ providedIn: 'root' })
export class AuthenticationService {
  private apiUrl = environment.apiBaseUrl + 'auth/';
  private isAuthenticatedSubject: BehaviorSubject<boolean>;
  public isAuthenticated$: Observable<boolean>;

  constructor(private http: HttpClient, private tokenService: TokenService, private router: Router) {
    this.isAuthenticatedSubject = new BehaviorSubject<boolean>(this.tokenService.hasToken());
    this.isAuthenticated$ = this.isAuthenticatedSubject.asObservable();
    this.checkTokenOnInit();
  }

  register(request: RegistrationRequest): Observable<number> {
    return this.http.post<number>(`${this.apiUrl}register`, request);
  }

  activateToken(token: string): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}activate-token/${token}`);
  }

  login(request: AuthenticationRequest): Observable<AuthenticationResponse> {
    return this.http.post<AuthenticationResponse>(`${this.apiUrl}login`, request, {
      withCredentials: true
    }).pipe(
      tap(response => {
        const token = response.accessToken || response.token;
        if (token) {
          this.tokenService.token = token;
          this.isAuthenticatedSubject.next(true);
          this.scheduleTokenRefresh();
        }
        sessionStorage.setItem('user', JSON.stringify(response.user));
      })
    );
  }

  refreshToken(): Observable<RefreshTokenResponse> {
    return this.http.post<RefreshTokenResponse>(`${this.apiUrl}refresh-token`, {}, {
      withCredentials: true
    }).pipe(
      tap(response => {
        if (response.accessToken) {
          this.tokenService.token = response.accessToken;
          this.isAuthenticatedSubject.next(true);
        }
      })
    );
  }

  logout(): Observable<void> {
    return this.http.post<void>(`${this.apiUrl}logout`, {}, {
      withCredentials: true
    }).pipe(
      tap(() => {
        this.tokenService.clearToken();
        this.isAuthenticatedSubject.next(false);
        this.router.navigate(['/login']);
      })
    );
  }

  private checkTokenOnInit(): void {
    if (this.tokenService.token && this.tokenService.isTokenExpired()) {
      this.tokenService.clearToken();
      this.isAuthenticatedSubject.next(false);
      this.router.navigate(['/login']);
    } else if (this.tokenService.hasToken()) {
      this.isAuthenticatedSubject.next(true);
      this.scheduleTokenRefresh();
    } else {
      this.router.navigate(['/login']);
    }
  }

  private scheduleTokenRefresh(): void {
    const expirationTime = this.tokenService.getTokenExpirationTime();
    if (!expirationTime) return;

    const currentTime = Date.now();
    const timeUntilExpiry = expirationTime - currentTime;
    const refreshTime = timeUntilExpiry - (5 * 60 * 1000);

    if (refreshTime > 0) {
      setTimeout(() => {
        this.refreshToken().subscribe({
          next: () => this.scheduleTokenRefresh(),
          error: () => this.logout().subscribe()
        });
      }, refreshTime);
    } else {
      this.logout().subscribe();
    }
  }
}
