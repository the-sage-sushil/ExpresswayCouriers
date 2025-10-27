import { Component } from '@angular/core';
import { Router, RouterModule } from '@angular/router';

@Component({
  selector: 'app-menu',
  templateUrl: './menu.component.html',
  imports: [RouterModule],
  styleUrls: ['./menu.component.scss'],
})
export class MenuComponent {
  isMobileMenuOpen = false;

  constructor(private router: Router) {}
  loggedInUser = JSON.parse(sessionStorage.getItem('user') || '{}');
  toggleMobileMenu() {
    this.isMobileMenuOpen = !this.isMobileMenuOpen;
  }

  closeMobileMenu() {
    this.isMobileMenuOpen = false;
  }

  logout() {
    sessionStorage.clear();
    localStorage.clear();
    this.router.navigate(['/login']).then(() => {
      window.location.reload();
    });
  }
}
