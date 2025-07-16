
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { Login } from './components/login/login';
import { Activation } from './components/register/activation/activation';
import { Register } from './components/register/register';

export const routes: Routes = [
  {
    path: 'login',
    component: Login
  },
  {
    path: 'activation',
    component: Activation
  },
  {
    path: 'consignments',
    loadChildren: () => import('./modules/consignment/consignment-module').then(m => m.ConsignmentModule)
  },
  {
    path: 'register',
    component: Register
  },
  { path: '', redirectTo: 'consignments', pathMatch: 'full' },
  { path: '**', redirectTo: 'consignments' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }

