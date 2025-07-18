import { NgModule } from "@angular/core";
import { RouterModule, Routes } from "@angular/router";
import { MainComponent } from "./pages/main/main.component";
import { ConsignmentBooking } from "./pages/consignment-booking/consignment-booking";
import { ConsignmentListComponent } from "./pages/consignment-list/consignment-list.component";

const moduleRoutes: Routes = [
  {
    path: '',
    component: MainComponent,
    children: [
      {
        path: '',
        component: ConsignmentListComponent
      },
      {
        path: 'booking',
        component: ConsignmentBooking // Assuming the same component is used for booking
      }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(moduleRoutes)],
  exports: [RouterModule]
})
export class ConsignmentRoutingModule { }
