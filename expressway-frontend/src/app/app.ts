import { Component, computed, signal } from '@angular/core';
import { Router, NavigationEnd, RouterOutlet } from '@angular/router';
import { MenuComponent } from './core/menu/menu.component';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [CommonModule, RouterOutlet, MenuComponent],
  templateUrl: './app.html',
  styleUrls: ['./app.css']
})
export class App {
  protected title = 'expressway-frontend';
  private currentUrl = signal('');
  protected showMenu = computed(() => {
    return !(
      this.currentUrl().includes('/login') ||
      this.currentUrl().includes('/register') ||
      this.currentUrl().includes('/activation')
    );
  });

  constructor(private router: Router) {
    this.router.events.subscribe(event => {
      if (event instanceof NavigationEnd) {
        this.currentUrl.set(event.urlAfterRedirects);
      }
    });
  }
}
