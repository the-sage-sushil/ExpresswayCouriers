
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
//   {
//     path: 'books',
//     loadChildren: () => import('./consignment/consignment-module').then(m => m.ConsignmentModule)
//   },
  {
    path: 'register',
    component: Register
  },
  { path: '', redirectTo: 'login', pathMatch: 'full' },
  { path: '**', redirectTo: 'login' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }

