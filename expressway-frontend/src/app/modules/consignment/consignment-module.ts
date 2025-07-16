import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MenuComponent } from './components/menu/menu.component';
import { RatingComponent } from './components/rating/rating.component';
import { BookListComponent } from './pages/book-list/book-list.component';
import { MainComponent } from './pages/main/main.component';
import { ConsignmentRoutingModule } from './consignment-routing.modules';



@NgModule({
  declarations: [
    
  ],
  imports: [
    CommonModule,
    MainComponent,
    ConsignmentRoutingModule
  ]
})
export class ConsignmentModule { }
