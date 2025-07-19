
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { Login } from './components/login/login';
import { Activation } from './components/register/activation/activation';
import { Register } from './components/register/register';
import { ConsignmentList } from './components/consignment-list/consignment-list.component';
import { ConsignmentBooking } from './components/consignment-booking/consignment-booking';

export const routes: Routes = [
  {
    path: 'register',
    component: Register
  },
  {
    path: 'activation',
    component: Activation
  },
  {
    path: 'login',
    component: Login
  },
  {
    path: 'consignments',
    component: ConsignmentList
  },
  {
    path: 'booking',
    component: ConsignmentBooking
  },

  { path: '', redirectTo: 'consignments', pathMatch: 'full' },
  { path: '**', redirectTo: 'consignments' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }

