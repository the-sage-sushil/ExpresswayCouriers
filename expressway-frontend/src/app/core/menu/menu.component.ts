import { Component, OnInit } from '@angular/core';
import { Router, RouterModule } from '@angular/router';

@Component({
  selector: 'app-menu',
  templateUrl: './menu.component.html',
  imports: [RouterModule],
  styleUrls: ['./menu.component.scss'],
})
export class MenuComponent {
  logout() {
    throw new Error('Method not implemented.');
  }
}
