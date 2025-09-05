import { Component, computed, signal } from '@angular/core';
import { Router, NavigationEnd, RouterOutlet } from '@angular/router';
import { MenuComponent } from './core/menu/menu.component';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [CommonModule, RouterOutlet, MenuComponent],
  templateUrl: './app.html',
  styleUrls: ['./app.css'],
})
export class App {
  protected title = 'expressway-frontend';
  showMenu = signal<boolean>(false);

  constructor(private router: Router) {
    this.router.events.subscribe((event) => {
      if (event instanceof NavigationEnd) {
        const hideMenuRoutes = ['/login', '/register', '/activation'];
        this.showMenu.set(!hideMenuRoutes.includes(event.url));
      }
    });
  }
}
