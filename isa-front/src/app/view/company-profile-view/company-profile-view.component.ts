import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl, FormBuilder, Validators, FormArray } from '@angular/forms';
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
      dateTime: ['', Validators.required],
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
    console.log(this.appointmentForm.value.dateTime);

    this.appointmentRequest.duration = 5;
    this.appointmentRequest.admin_id = this.user.id;
    this.appointmentRequest.dateTime = new Date((this.appointmentForm.value.dateTime).setUTCHours(12, 30));
    this.appointmentService.addPredefinedAppointment(this.appointmentRequest).subscribe(result => {
      this.messageService.add({ severity: "success", summary: "creating appointment succedded" });
    })
  }
}
