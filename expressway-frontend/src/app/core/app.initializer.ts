import { inject } from '@angular/core';
import { AuthenticationService } from '../services/authentication.service';

export function appInitializer() {
  const authService = inject(AuthenticationService);
  return () => {
    // This will trigger the token check in the constructor
    return Promise.resolve();
  };
}