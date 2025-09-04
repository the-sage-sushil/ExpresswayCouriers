import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class  TokenService {

  private readonly TOKEN_KEY = 'token';

  set token(token: string) {
    console.log('Setting token:', token); // Debug log
    if (token) {
      localStorage.setItem(this.TOKEN_KEY, token);
      console.log('Token stored in localStorage:', localStorage.getItem(this.TOKEN_KEY)); // Debug log
    } else {
      this.clearToken();
      console.log('Token cleared from localStorage'); // Debug log
    }
  }

  get token(): string | null {
    const token = localStorage.getItem(this.TOKEN_KEY);
    console.log('Getting token from localStorage:', token); // Debug log
    return token;
  }

  clearToken(): void {
    localStorage.removeItem(this.TOKEN_KEY);
  }

  hasToken(): boolean {
    return !!this.token;
  }
}

