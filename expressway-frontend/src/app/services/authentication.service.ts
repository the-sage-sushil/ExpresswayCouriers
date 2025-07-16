import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { AuthenticationRequest, AuthenticationResponse, RegistrationRequest } from './models/authentication.model';
import { environment } from '../../environments/environment';

@Injectable({ providedIn: 'root' })
export class AuthenticationService {
  private apiUrl = environment.apiBaseUrl + 'auth/';

  constructor(private http: HttpClient) {}

  register(request: RegistrationRequest): Observable<number> {
    return this.http.post<number>(`${this.apiUrl}register`, request, { withCredentials: true });
  }

  activateToken(token: string): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}activate-token/${token}`, { withCredentials: true });
  }

  login(request: AuthenticationRequest): Observable<AuthenticationResponse> {
    return this.http.post<AuthenticationResponse>(`${this.apiUrl}login`, request, { withCredentials: true });
  }
}
