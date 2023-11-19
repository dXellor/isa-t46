import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { NavbarComponent } from './component/navbar/navbar.component';
import { MatButtonModule } from '@angular/material/button';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { LoginViewComponent } from './view/login-view/login-view.component'
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { TokenInterceptor } from './util/token-interceptor';
import { RegisterViewComponent } from './view/register-view/register-view.component';
import { VerificationViewComponent } from './view/verification-view/verification-view.component';
import { UserManagingViewComponent } from './view/user-managing-view/user-managing-view.component';
import { CompanyAdminFormComponent } from './component/company-admin-form/company-admin-form.component';
import { CompanyFormComponent } from './component/company-form/company-form.component';
import { CompanyManagingViewComponent } from './view/company-managing-view/company-managing-view.component';
import { UserEditComponent } from './view/user-edit/user-edit.component';

@NgModule({
  declarations: [
    AppComponent,
    NavbarComponent,
    LoginViewComponent,
    RegisterViewComponent,
    VerificationViewComponent,
    UserManagingViewComponent,
    CompanyAdminFormComponent,
    CompanyFormComponent,
    CompanyManagingViewComponent,
    UserEditComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    MatButtonModule,
    FormsModule,
    ReactiveFormsModule,
    MatFormFieldModule,
    MatInputModule,
    HttpClientModule,
    BrowserAnimationsModule
  ],
  providers: [
    {
      provide: HTTP_INTERCEPTORS,
      useClass: TokenInterceptor,
      multi: true
    },
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
