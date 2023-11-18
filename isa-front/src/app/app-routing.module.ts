import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginViewComponent } from './view/login-view/login-view.component';
import { RegisterViewComponent } from './view/register-view/register-view.component';

const routes: Routes = [
  { path: 'login', component: LoginViewComponent, },
  { path: 'register', component: RegisterViewComponent, },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
