import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { HTTP_INTERCEPTORS, provideHttpClient } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { App } from './app';
import { AppRoutingModule } from './app.routes';
import { HttpTokenInterceptor } from './services/interceptor/http-token.interceptor';
import { CodeInputModule } from 'angular-code-input';
import { ConsignmentRoutingModule } from './modules/consignment/consignment-routing.modules';

@NgModule({
  declarations: [
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    ConsignmentRoutingModule,
    CodeInputModule,
    FormsModule,
    App,
  ],
  providers: [
    provideHttpClient(),
    {
      provide: HTTP_INTERCEPTORS,
      useClass: HttpTokenInterceptor,
      multi: true
    }
  ],
})
export class AppModule { }
