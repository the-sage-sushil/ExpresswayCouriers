import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AuthenticationService } from '../../services/authentication.service';
import { TokenService } from '../../services/token.service';
import { RegistrationRequest } from '../../services/models/authentication.model';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [FormsModule, CommonModule],
  templateUrl: './register.html',
  styleUrl: './register.css',
})
export class Register {
  registerRequest: RegistrationRequest = {
    email: '',
    firstName: '',
    lastName: '',
    password: '',
  };
  
  errorMsg: Array<string> = [];
  
  passwordCriteria = {
    minLength: false,
    hasLowercase: false,
    hasUppercase: false,
    hasNumber: false,
    hasSpecialChar: false
  };
  
  showPasswordCriteria = false;

  constructor(
    private router: Router,
    private authService: AuthenticationService,
    private tokenService: TokenService
  ) {}

  login() {
    this.router.navigate(['login']);
  }

  onPasswordChange() {
    const password = this.registerRequest.password || '';
    this.showPasswordCriteria = true;
    
    this.passwordCriteria = {
      minLength: password.length >= 8,
      hasLowercase: /[a-z]/.test(password),
      hasUppercase: /[A-Z]/.test(password),
      hasNumber: /\d/.test(password),
      hasSpecialChar: /[@$!%*?&]/.test(password)
    };
  }
  
  onPasswordFocus() {
    this.showPasswordCriteria = true;
    this.onPasswordChange(); // Initialize criteria when focused
  }
  
  isPasswordValid(): boolean {
    if (!this.registerRequest.password) return false;
    return Object.values(this.passwordCriteria).every(criteria => criteria);
  }

  register() {
    this.errorMsg = [];
    
    // Validate required fields
    if (!this.registerRequest.firstName?.trim()) {
      this.errorMsg.push('First name is required');
    }
    if (!this.registerRequest.lastName?.trim()) {
      this.errorMsg.push('Last name is required');
    }
    if (!this.registerRequest.email?.trim()) {
      this.errorMsg.push('Email is required');
    }
    if (!this.registerRequest.password) {
      this.errorMsg.push('Password is required');
    } else if (!this.isPasswordValid()) {
      this.errorMsg.push('Password does not meet the required criteria');
    }
    
    if (this.errorMsg.length > 0) {
      return;
    }
    
    this.authService.register(this.registerRequest).subscribe({
      next: (res: any): void => {
        this.router.navigate(['activation']);
      },
      error: (err): void => {
        console.log(err);
        if (err.error.validationErrors) {
          this.errorMsg = err.error.validationErrors;
        } else {
          this.errorMsg.push(err.error.businessExceptionDescription);
        }
      },
    });
  }
}
