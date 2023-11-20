import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginViewComponent } from './view/login-view/login-view.component';
import { RegisterViewComponent } from './view/register-view/register-view.component';
import { VerificationViewComponent } from './view/verification-view/verification-view.component';
import { UserManagingViewComponent } from './view/user-managing-view/user-managing-view.component';
import { CompanyManagingViewComponent } from './view/company-managing-view/company-managing-view.component';
import { UserEditComponent } from './view/user-edit/user-edit.component';
import { authGuard } from './guards/auth.guard';
import { EquipmentCollectionViewComponent } from './view/equipment-collection-view/equipment-collection-view.component';

const routes: Routes = [
  { path: 'login', component: LoginViewComponent, },
  { path: 'register', component: RegisterViewComponent},
  { path: 'verify/:token', component: VerificationViewComponent, },
  { path: 'manage-users', component: UserManagingViewComponent, },
  { path: 'manage-companies', component: CompanyManagingViewComponent, },
  { path: 'equipment-collection', component: EquipmentCollectionViewComponent, },
  { path: 'user-edit', component: UserEditComponent},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
