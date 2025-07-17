import { Component } from '@angular/core';
import { AppRoutingModule } from "../../../../app.routes";

@Component({
  selector: 'app-main',
  templateUrl: './main.component.html',
  standalone: false,
  styleUrls: ['./main.component.scss'],
})
export class MainComponent {
 title = 'book-network-ui';
}
