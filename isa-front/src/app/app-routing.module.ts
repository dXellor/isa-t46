import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginViewComponent } from './view/login-view/login-view.component';
import { RegisterViewComponent } from './view/register-view/register-view.component';
import { VerificationViewComponent } from './view/verification-view/verification-view.component';
import { UserManagingViewComponent } from './view/user-managing-view/user-managing-view.component';
import { CompanyManagingViewComponent } from './view/company-managing-view/company-managing-view.component';

const routes: Routes = [
  { path: 'login', component: LoginViewComponent, },
  { path: 'register', component: RegisterViewComponent},
  { path: 'verify/:token', component: VerificationViewComponent, },
  { path: 'manage-users', component: UserManagingViewComponent, },
  { path: 'manage-companies', component: CompanyManagingViewComponent, },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
