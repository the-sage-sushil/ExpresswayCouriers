import { HttpInterceptorFn } from '@angular/common/http';
import { inject } from '@angular/core';
import { TokenService } from '../token.service';

export const httpTokenInterceptor: HttpInterceptorFn = (req, next) => {
  console.log('Interceptor called for URL:', req.url); // Debug log
  
  const tokenService = inject(TokenService);
  const token = tokenService.token;
  
  console.log('Token from service:', token); // Debug log
  console.log('Current request headers:', req.headers.keys()); // Debug log

  if (token) {
    const authReq = req.clone({
      setHeaders: {
        Authorization: `Bearer ${token}`
      }
    });
    console.log('Auth header added, final headers:', authReq.headers.keys()); // Debug log
    console.log('Authorization header:', authReq.headers.get('Authorization')); // Debug log
    return next(authReq);
  }

  console.log('No token found, proceeding without authorization'); // Debug log
  return next(req);
}
