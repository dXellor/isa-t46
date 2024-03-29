import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { NavbarComponent } from './component/navbar/navbar.component';
import { MatButtonModule } from '@angular/material/button';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatSelectModule } from '@angular/material/select';
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
import { CompanyProfileViewComponent } from './view/company-profile-view/company-profile-view.component';
import { MatIconModule } from '@angular/material/icon';
import { MatCardModule } from '@angular/material/card';
import { FlexLayoutModule } from '@angular/flex-layout';
import { EquipmentCollectionViewComponent } from './view/equipment-collection-view/equipment-collection-view.component';
import { MapComponent } from './component/map/map.component';
import { NgOptimizedImage } from "@angular/common";
import { ToastModule } from "primeng/toast";
import { MessageModule } from "primeng/message";
import { MatStepperModule } from "@angular/material/stepper";
import { MessagesModule } from "primeng/messages";
import { MatSidenavModule } from '@angular/material/sidenav';
import { CompanyCalendarViewComponent } from './view/company-calendar-view/company-calendar-view.component';
import { CalendarModule, DateAdapter } from 'angular-calendar';
import { adapterFactory } from 'angular-calendar/date-adapters/date-fns';
import { SystemAdminFormComponent } from './component/system-admin-form/system-admin-form.component';
import { PasswordChangeFormComponent } from './component/password-change-form/password-change-form.component';
import { UserHomeViewComponent } from './view/user-home-view/user-home-view.component';
import { CompanyCardComponent } from './component/company-card/company-card.component';
import { CompanyInfoViewComponent } from './view/company-info-view/company-info-view.component';
import { EquipmentSelectorComponent } from './component/equipment-selector/equipment-selector.component';
import { MatDialogModule } from '@angular/material/dialog';
import { UserAppointmentFormComponent } from './component/user-appointment-form/user-appointment-form.component';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatNativeDateModule } from '@angular/material/core';
import { EmployeeReservationsViewComponent } from './view/employee-reservations-view/employee-reservations-view.component';
import { MatTableModule } from '@angular/material/table';
import { CompanyAdminAppointmentsComponent } from './component/company-admin-appointments/company-admin-appointments.component';
import { CompanyInventoryManagementComponent } from './component/company-inventory-management/company-inventory-management.component';
import { VisitorHomeViewComponent } from './view/visitor-home-view/visitor-home-view.component';
import { EquipmentCardComponent } from './component/equipment-card/equipment-card.component';
import { UserCompletedReservationsComponent } from './view/user-completed-reservations/user-completed-reservations.component';
import { UserAppointmentsQrCodesComponent } from './view/user-appointments-qr-codes/user-appointments-qr-codes.component';
import { PositionSimulatorViewComponent } from './view/position-simulator-view/position-simulator-view.component';
import { IMqttServiceOptions, MqttModule } from 'ngx-mqtt';
import { CompanyAdminReservationsViewComponent } from './view/company-admin-reservations-view/company-admin-reservations-view.component';
import {ConfirmDialogModule} from "primeng/confirmdialog";

const MQTT_SERVICE_OPTIONS: IMqttServiceOptions = {
  hostname: 'localhost',
  port: 9001,
  clean: true,
  connectTimeout: 4000,
  reconnectPeriod: 4000,
  clientId: 'CONSUMER46',
  protocol: 'ws'
}
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
    CompanyProfileViewComponent,
    EquipmentCollectionViewComponent,
    MapComponent,
    CompanyCalendarViewComponent,
    SystemAdminFormComponent,
    PasswordChangeFormComponent,
    UserHomeViewComponent,
    CompanyCardComponent,
    CompanyInfoViewComponent,
    EquipmentSelectorComponent,
    UserAppointmentFormComponent,
    EmployeeReservationsViewComponent,
    CompanyAdminAppointmentsComponent,
    CompanyInventoryManagementComponent,
    VisitorHomeViewComponent,
    EquipmentCardComponent,
    UserCompletedReservationsComponent,
    PositionSimulatorViewComponent,
    CompanyAdminReservationsViewComponent,
    UserAppointmentsQrCodesComponent,
  ],

  imports: [
    BrowserModule,
    AppRoutingModule,
    MatButtonModule,
    FormsModule,
    ReactiveFormsModule,
    MatFormFieldModule,
    MatSelectModule,
    MatInputModule,
    HttpClientModule,
    BrowserAnimationsModule,
    MatIconModule,
    MatCardModule,
    FlexLayoutModule,
    NgOptimizedImage,
    ToastModule,
    MessageModule,
    MatStepperModule,
    MessagesModule,
    MatSidenavModule,
    CalendarModule.forRoot({
      provide: DateAdapter,
      useFactory: adapterFactory,
    }),
    MatDialogModule,
    MatDatepickerModule,
    MatNativeDateModule,
    MatTableModule,
    MqttModule.forRoot(MQTT_SERVICE_OPTIONS),
    ConfirmDialogModule
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
