
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
import { InvoiceGeneratorComponent } from './components/invoice-generator/invoice-generator.component';
import { authGuard } from './guards/auth.guard';

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
    component: ConsignmentList,
    canActivate: [authGuard]
  },
  {
    path: 'booking',
    component: ConsignmentBooking,
    canActivate: [authGuard]
  },
  {
    path: 'billings',
    component: InvoiceGeneratorComponent,
    canActivate: [authGuard]
  },
  {
    path: 'client',
    component: ClientList,
    canActivate: [authGuard]
  },
  {
    path: 'client/new',
    component: ClientCreate,
    canActivate: [authGuard]
  },
  { path: 'client/edit/:id',
    component: ClientEdit,
    canActivate: [authGuard]
  },
  {
    path: 'invoice',
    component: InvoiceGeneratorComponent,
    canActivate: [authGuard]
  },

  { path: '', redirectTo: 'login', pathMatch: 'full' },
  { path: '**', redirectTo: 'client' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }

