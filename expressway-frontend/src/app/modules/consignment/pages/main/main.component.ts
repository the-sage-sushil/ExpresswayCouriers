import { Component } from '@angular/core';
import { AppRoutingModule } from "../../../../app.routes";
import { MenuComponent } from "../../components/menu/menu.component";

@Component({
  selector: 'app-main',
  templateUrl: './main.component.html',
  styleUrls: ['./main.component.scss'],
  imports: [AppRoutingModule, MenuComponent]
})
export class MainComponent {
 title = 'book-network-ui';
}
