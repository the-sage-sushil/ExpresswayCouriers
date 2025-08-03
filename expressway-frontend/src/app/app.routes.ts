
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { Login } from './components/login/login';
import { Activation } from './components/register/activation/activation';
import { Register } from './components/register/register';
import { ConsignmentList } from './components/consignment-list/consignment-list.component';
import { ConsignmentBooking } from './components/consignment-booking/consignment-booking';
import { ClientList } from './components/client/client';
import { ClientCreate } from './components/client/create/client-create';
import { ClientEdit } from './components/client/edit/client-edit';

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
  {
    path: 'client',
    component: ClientList
  },
  {
    path: 'client/new',
    component: ClientCreate
  },
  { path: 'client/edit/:id',
    component: ClientEdit 
  },

  { path: '', redirectTo: 'login', pathMatch: 'full' },
  { path: '**', redirectTo: 'client' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }

