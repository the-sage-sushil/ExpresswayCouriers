import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class TokenService {

  private readonly TOKEN_KEY = 'accessToken';

  set token(token: string) {
    if (token) {
      sessionStorage.setItem(this.TOKEN_KEY, token);
    } else {
      this.clearToken();
    }
  }

  get token(): string | null {
    return sessionStorage.getItem(this.TOKEN_KEY);
  }

  clearToken(): void {
    sessionStorage.removeItem(this.TOKEN_KEY);
  }

  hasToken(): boolean {
    return !!this.token && !this.isTokenExpired();
  }

  isTokenExpired(): boolean {
    const token = this.token;
    if (!token) return true;

    try {
      const payload = JSON.parse(atob(token.split('.')[1]));
      const currentTime = Math.floor(Date.now() / 1000);
      return payload.exp < currentTime;
    } catch (error) {
      return true;
    }
  }

  getTokenExpirationTime(): number | null {
    const token = this.token;
    if (!token) return null;

    try {
      const payload = JSON.parse(atob(token.split('.')[1]));
      return payload.exp * 1000;
    } catch (error) {
      return null;
    }
  }
}

