import { HttpInterceptorFn, HttpErrorResponse } from '@angular/common/http';
import { inject } from '@angular/core';
import { Router } from '@angular/router';
import { TokenService } from '../token.service';
import { AuthenticationService } from '../authentication.service';
import { catchError, switchMap, throwError } from 'rxjs';

export const httpTokenInterceptor: HttpInterceptorFn = (req, next) => {
  const tokenService = inject(TokenService);
  const authService = inject(AuthenticationService);
  const router = inject(Router);
  
  // Add token to non-auth requests
  if (!req.url.includes('/auth/')) {
    const token = tokenService.token;
    if (token) {
      req = req.clone({
        setHeaders: {
          Authorization: `Bearer ${token}`
        }
      });
    }
  }

  return next(req).pipe(
    catchError((error: HttpErrorResponse) => {
      if (error.status === 401 && !req.url.includes('/auth/')) {
        return authService.refreshToken().pipe(
          switchMap((response) => {
            const newReq = req.clone({
              setHeaders: {
                Authorization: `Bearer ${response.accessToken}`
              }
            });
            return next(newReq);
          }),
          catchError((refreshError) => {
            authService.logout().subscribe({
              complete: () => router.navigate(['/login'])
            });
            return throwError(() => refreshError);
          })
        );
      }
      return throwError(() => error);
    })
  );
}
