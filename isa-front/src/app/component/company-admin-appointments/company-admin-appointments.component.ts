import { Component, OnInit } from '@angular/core';
import { AbstractControl, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MessageService } from 'primeng/api';
import { AppointmentRequest } from 'src/app/model/appointment/appointment-request.model';
import { Company } from 'src/app/model/company.model';
import { Reservation } from 'src/app/model/reservation/reservation.model';
import { User } from 'src/app/model/user.model';
import { CompanyService } from 'src/app/service/company.service';
import { AppointmentService } from 'src/app/service/reservation/appointment.service';
import { ReservationService } from 'src/app/service/reservation/reservation.service';
import { UserService } from 'src/app/service/user.service';

@Component({
  selector: 'app-company-admin-appointments',
  templateUrl: './company-admin-appointments.component.html',
  styleUrls: ['./company-admin-appointments.component.css'],
  providers: [MessageService]
})
export class CompanyAdminAppointmentsComponent implements OnInit {

  constructor(private messageService: MessageService, private reservationService: ReservationService, private appointmentService: AppointmentService, private formBuilder: FormBuilder, private companyService: CompanyService, private userService: UserService) { }

  reservations: Reservation[] = [];
  appointmentRequest: AppointmentRequest = {} as AppointmentRequest;
  public appointmentForm: FormGroup;
  public company: Company;
  public user: User;
  displayedColumns: string[] = ['dateTime', 'duration', 'companyAdmin'];
  dataSource: Reservation[] = [];

  ngOnInit(): void {
    this.user = this.userService.getCurrentUser();
    this.companyService.getCompanyByAdminId(this.user.id).subscribe(company => {
      this.company = company;
      this.initAppointmentForm();
    });
    this.loadAdminResevations();
  }

  private initAppointmentForm(): void {
    this.appointmentForm = this.formBuilder.group({
      admin: ['', Validators.required],
      dateTime: ['', Validators.required],
      time: ['', [Validators.required, Validators.pattern(/^(0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]$/), this.timeWithinWorkingHours.bind(this)]],
      duration: ['', Validators.required],
    });
  }

  loadAdminResevations(): void {
    this.reservationService.getReservationsForCompanyAdmin().subscribe(result => {
      this.dataSource = result.content;
    })
  }

  addAppointment(): void {
    this.appointmentRequest.duration = this.appointmentForm.value.duration;
    this.appointmentRequest.admin_id = this.appointmentForm.value.admin.id;
    const [hours, minutes] = this.appointmentForm.value.time.split(':').map(Number);
    this.appointmentRequest.dateTime = new Date((this.appointmentForm.value.dateTime).setUTCHours(hours, minutes));
    this.appointmentService.addPredefinedAppointment(this.appointmentRequest).subscribe(result => {
      this.messageService.add({ severity: "success", summary: "creating appointment succedded" });
    })
  }

  dateFilter = (date: Date | null): boolean => {
    const day = (date || new Date()).getDay();
    return day !== 0 && day !== 6 && date >= new Date();
  }

  timeWithinWorkingHours(control: AbstractControl): { [key: string]: any } | null {
    const [startHour, startMinute] = this.company.startWork.split(':').map(Number);
    const [endHour, endMinute] = this.company.endWork.split(':').map(Number);
    const [hour, minute] = control.value.split(':').map(Number);
    const isValid = (hour > startHour || (hour === startHour && minute >= startMinute)) &&
      (hour < endHour || (hour === endHour && minute <= endMinute));
    return isValid ? null : { 'timeOutsideWorkingHours': { value: control.value } };
  }

  formatDate(dateT: Date): string {
    const dateTime = new Date(dateT);
    const date = `${dateTime.getDate()}.${dateTime.getMonth() + 1}.${dateTime.getFullYear()}`;
    const time = `${dateTime.getHours()}:${String(dateTime.getMinutes()).padStart(2, '0')}`;
    return `${date} - ${time}`
  }
}
