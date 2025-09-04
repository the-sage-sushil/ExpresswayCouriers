import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { App } from './app';
import { AppRoutingModule } from './app.routes';
import { CodeInputModule } from 'angular-code-input';
import { NgSelectModule } from '@ng-select/ng-select';

@NgModule({
  declarations: [
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    CodeInputModule,
    FormsModule,
    NgSelectModule,
    ReactiveFormsModule,
    App,
  ],
  providers: [],
})
export class AppModule { }
