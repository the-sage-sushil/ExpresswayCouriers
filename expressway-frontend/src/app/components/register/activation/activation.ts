
import { skipUntil } from 'rxjs';
import { AuthenticationService } from '../../../services/authentication.service';
import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AppModule } from '../../../app.module';
import { CodeInputModule } from "angular-code-input";
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-activation',
  imports: [FormsModule,CommonModule,CodeInputModule],
  templateUrl: './activation.html',
  styleUrl: './activation.css'
})
export class Activation {

  message = '';
  isOkay = true;
  submitted = false;
  constructor(
    private router: Router,
    private authService: AuthenticationService
  ) {}

  private confirmAccount(token: string) {
    this.authService.activateToken(token
    ).subscribe({
      next: () => {
        this.message = 'Your account has been successfully activated.\nNow you can proceed to login';
        this.submitted = true;
      },
      error: () => {
        this.message = 'Token has been expired or invalid';
        this.submitted = true;
        this.isOkay = false;
      }
    });
  }

  redirectToLogin() {
    this.router.navigate(['login']);
  }

  onCodeCompleted(token: string) {
    this.confirmAccount(token);
  }

  protected readonly skipUntil = skipUntil;
}

