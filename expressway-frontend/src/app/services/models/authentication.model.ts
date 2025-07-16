// Authentication request for login
export interface AuthenticationRequest {
  email: string;
  password: string;
}

// Authentication response after login
export interface AuthenticationResponse {
  token: string;
  userId: number;
  // add other fields as needed
}

// User registration request
export interface RegistrationRequest {
  email: string;
  firstName: string;
  lastName: string;
  password: string;
}
