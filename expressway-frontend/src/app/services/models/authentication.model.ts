// Authentication request for login
export interface AuthenticationRequest {
  email: string;
  password: string;
}

// Authentication response after login
export interface AuthenticationResponse {
  token?: string; // Keep for backward compatibility
  accessToken: string;
  user: Object;
  message: string;
}

// Refresh token response
export interface RefreshTokenResponse {
  accessToken: string;
  message: string;
}

// User registration request
export interface RegistrationRequest {
  email: string;
  firstName: string;
  lastName: string;
  password: string;
}
