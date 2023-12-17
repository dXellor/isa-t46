import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl, FormBuilder, Validators, FormArray, AbstractControl } from '@angular/forms';
import { MessageService } from 'primeng/api';
import { AppointmentRequest } from 'src/app/model/appointment/appointment-request.model';
import { Company } from 'src/app/model/company.model';
import { User } from 'src/app/model/user.model';
import { AppointmentService } from 'src/app/service/reservation/appointment.service';
import { CompanyService } from 'src/app/service/company.service';
import { UserService } from 'src/app/service/user.service';
import { Reservation } from 'src/app/model/reservation/reservation.model';

@Component({
  selector: 'app-company-profile-view',
  templateUrl: './company-profile-view.component.html',
  styleUrls: ['./company-profile-view.component.css'],
  providers: [MessageService]
})
export class CompanyProfileViewComponent implements OnInit {
  public company: Company;
  public companyForm: FormGroup;
  public editMode = false;
  public user: User;
  public appointmentForm: FormGroup;
  appointmentRequest: AppointmentRequest = {} as AppointmentRequest;
  workingHours: string = '08:00-16:00';

  constructor(private userService: UserService, private companyService: CompanyService, private formBuilder: FormBuilder, private appointmentService: AppointmentService, private messageService: MessageService) { }

  ngOnInit(): void {
    this.userService.setLoggedInUser();
    this.userService.loggedInUserTrigger.subscribe(user => {
      this.user = user;
    });
    this.companyService.getCompanyByAdminId(this.user.id).subscribe(company => {
      this.company = company;
      this.companyForm = this.formBuilder.group({
        id: [this.company.id],
        name: [this.company.name, Validators.required],
        description: [this.company.description, Validators.required],
        averageRating: [this.company.averageRating, [Validators.required, Validators.min(1), Validators.max(5)]],
        address: this.formBuilder.group({
          id: [this.company.address.id],
          street: [this.company.address.street, Validators.required],
          city: [this.company.address.city, Validators.required],
          country: [this.company.address.country, Validators.required],
          zipCode: [this.company.address.zipCode, [Validators.required, Validators.pattern('^[0-9]{5}$')]]
        }),
        equipment: this.formBuilder.array(this.company.equipment || []),
        admins: this.formBuilder.array(this.company.admins || [])
      });
      this.companyForm.disable();
    });

    this.appointmentForm = this.formBuilder.group({
      admin: ['', Validators.required],
      dateTime: ['', Validators.required],
      time: ['', [Validators.required, Validators.pattern(/^(0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]$/), this.timeWithinWorkingHours.bind(this)]],
      duration: ['', Validators.required],
    })
  }

  toggleEditMode(): void {
    this.editMode = !this.companyForm.enabled;
    if (this.editMode) {
      this.companyForm.enable();
    } else {
      this.companyForm.disable();
      this.companyForm.reset(this.company);
    }
  }

  onSubmit(): void {
    if (this.companyForm.valid) {
      this.company = this.companyForm.value;
      this.toggleEditMode();
      this.companyService.updateCompany(this.company).subscribe(company => {
        this.company = company;
        this.companyForm.patchValue(company);

      });
    }
  }

  removeEquipment(index: number): void {
    const equipment = this.companyForm.get('equipment') as FormArray;
    equipment.removeAt(index);
    this.company.equipment.splice(index, 1);
  }

  isRatingField(controlName: string): boolean {
    return controlName === 'averageRating';
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
    const [startTime, endTime] = this.workingHours.split('-');
    const [startHour, startMinute] = startTime.split(':').map(Number);
    const [endHour, endMinute] = endTime.split(':').map(Number);
    const [hour, minute] = control.value.split(':').map(Number);
    const isValid = (hour > startHour || (hour === startHour && minute >= startMinute)) &&
      (hour < endHour || (hour === endHour && minute <= endMinute));
    return isValid ? null : { 'timeOutsideWorkingHours': { value: control.value } };
  }
}
