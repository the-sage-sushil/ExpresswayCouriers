import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MenuComponent } from './components/menu/menu.component';
import { RatingComponent } from './components/rating/rating.component';
import { MainComponent } from './pages/main/main.component';
import { ConsignmentRoutingModule } from './consignment-routing.modules';
import { ConsignmentListComponent } from './pages/consignment-list/consignment-list.component';
import { ReactiveFormsModule } from '@angular/forms';



@NgModule({
  declarations: [
    MainComponent,
    MenuComponent,
    ConsignmentListComponent,
  ],
  imports: [
    CommonModule,
    RatingComponent,
    ReactiveFormsModule,
    ConsignmentRoutingModule
  ]
})
export class ConsignmentModule { }
