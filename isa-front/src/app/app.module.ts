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
import {NgOptimizedImage} from "@angular/common";
import { ToastModule} from "primeng/toast";
import { MessageModule} from "primeng/message";
import {MatStepperModule} from "@angular/material/stepper";
import {MessagesModule} from "primeng/messages";
import {MatSidenavModule} from '@angular/material/sidenav';
import { CompanyCalendarViewComponent } from './view/company-calendar-view/company-calendar-view.component';
import { CalendarModule, DateAdapter } from 'angular-calendar';
import { adapterFactory } from 'angular-calendar/date-adapters/date-fns';
import { NgOptimizedImage } from "@angular/common";
import { ToastModule } from "primeng/toast";
import { MessageModule } from "primeng/message";
import { MatStepperModule } from "@angular/material/stepper";
import { MessagesModule } from "primeng/messages";
import { MatSidenavModule } from '@angular/material/sidenav';
import { UserHomeViewComponent } from './view/user-home-view/user-home-view.component';
import { CompanyCardComponent } from './component/company-card/company-card.component';
import { CompanyInfoViewComponent } from './view/company-info-view/company-info-view.component';
import { EquipmentSelectorComponent } from './component/equipment-selector/equipment-selector.component';
import { MatDialogModule } from '@angular/material/dialog';
import { UserAppointmentFormComponent } from './component/user-appointment-form/user-appointment-form.component';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatNativeDateModule } from '@angular/material/core';

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
    UserHomeViewComponent,
    CompanyCardComponent,
    CompanyInfoViewComponent,
    EquipmentSelectorComponent,
    UserAppointmentFormComponent,
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
    MatDialogModule,
    MatDatepickerModule,
    MatNativeDateModule,
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
