import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginViewComponent } from './view/login-view/login-view.component';
import { RegisterViewComponent } from './view/register-view/register-view.component';
import { VerificationViewComponent } from './view/verification-view/verification-view.component';
import { UserManagingViewComponent } from './view/user-managing-view/user-managing-view.component';
import { CompanyManagingViewComponent } from './view/company-managing-view/company-managing-view.component';
import { UserEditComponent } from './view/user-edit/user-edit.component';
import { authGuard } from './guards/auth.guard';
import { CompanyProfileViewComponent } from './view/company-profile-view/company-profile-view.component';
import { EquipmentCollectionViewComponent } from './view/equipment-collection-view/equipment-collection-view.component';
import { CompanyCalendarViewComponent } from './view/company-calendar-view/company-calendar-view.component';
import { UserHomeViewComponent } from "./view/user-home-view/user-home-view.component";
import { CompanyInfoViewComponent } from "./view/company-info-view/company-info-view.component";
import { EmployeeReservationsViewComponent } from './view/employee-reservations-view/employee-reservations-view.component';
import { VisitorHomeViewComponent } from './view/visitor-home-view/visitor-home-view.component';
import { UserCompletedReservationsComponent } from './view/user-completed-reservations/user-completed-reservations.component';
import { UserAppointmentsQrCodesComponent } from './view/user-appointments-qr-codes/user-appointments-qr-codes.component'
import { PositionSimulatorViewComponent } from './view/position-simulator-view/position-simulator-view.component';
import {
  CompanyAdminReservationsViewComponent
} from "./view/company-admin-reservations-view/company-admin-reservations-view.component";


const routes: Routes = [
  { path: 'login', component: LoginViewComponent, },
  { path: 'register', component: RegisterViewComponent },
  { path: 'verify/:token', component: VerificationViewComponent, },
  { path: 'manage-users', component: UserManagingViewComponent, },
  { path: 'manage-companies', component: CompanyManagingViewComponent, },
  { path: 'user-edit', component: UserEditComponent, canActivate: [authGuard] },
  { path: 'company-profile', component: CompanyProfileViewComponent, canActivate: [authGuard] },
  { path: 'equipment-collection', component: EquipmentCollectionViewComponent, },
  { path: 'company-calendar', component: CompanyCalendarViewComponent, },
  { path: 'user-home', component: UserHomeViewComponent },
  { path: 'company-info', component: CompanyInfoViewComponent },
  { path: 'employee-reservations', component: EmployeeReservationsViewComponent },
  { path: '', component: VisitorHomeViewComponent },
  { path: 'user-completed-reservations', component: UserCompletedReservationsComponent },
  { path: 'position-simulator', component: PositionSimulatorViewComponent },
  { path: 'manage-reservations', component: CompanyAdminReservationsViewComponent, canActivate: [authGuard] },
  { path: 'user-appointments-qr-codes', component: UserAppointmentsQrCodesComponent},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
