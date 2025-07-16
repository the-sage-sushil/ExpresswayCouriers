import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthenticationService } from '../../services/authentication.service';
import { TokenService } from '../../services/token.service';
import { AuthenticationRequest, AuthenticationResponse } from '../../services/models/authentication.model';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-login',
  imports: [FormsModule,CommonModule],
  templateUrl: './login.html',
  styleUrl: './login.css',
})
export class Login {
  constructor(
    private router: Router,
    private authService: AuthenticationService,
    private tokenService: TokenService
  ) {}

  authRequest: AuthenticationRequest = { email: '', password: '' };
  errorMsg: Array<string> = [];

  register() {
    this.router.navigate(['register']);
  }
  login() {
    this.errorMsg = [];
    this.authService.login(this.authRequest).subscribe({
      next: (res: AuthenticationResponse): void => {
        this.tokenService.token = res.token as string;
        this.router.navigate(['consignments']);
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
