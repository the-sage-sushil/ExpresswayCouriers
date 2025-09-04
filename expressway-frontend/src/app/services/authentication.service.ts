import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, tap } from 'rxjs';
import { AuthenticationRequest, AuthenticationResponse, RegistrationRequest } from './models/authentication.model';
import { environment } from '../../environments/environment';
import { TokenService } from './token.service';

@Injectable({ providedIn: 'root' })
export class AuthenticationService {
  private apiUrl = environment.apiBaseUrl + 'auth/';

  constructor(private http: HttpClient, private tokenService: TokenService) {}

  register(request: RegistrationRequest): Observable<number> {
    return this.http.post<number>(`${this.apiUrl}register`, request);
  }

  activateToken(token: string): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}activate-token/${token}`);
  }

  login(request: AuthenticationRequest): Observable<AuthenticationResponse> {
    return this.http.post<AuthenticationResponse>(`${this.apiUrl}login`, request)
      .pipe(
        tap(response => {
          if (response.token) {
            this.tokenService.token = response.token;
          }
        })
      );
  }
}
