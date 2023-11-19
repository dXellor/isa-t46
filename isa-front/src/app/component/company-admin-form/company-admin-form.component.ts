import { Component, EventEmitter, Output } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { AuthService } from '../../service/auth.service'
import { User } from '../../model/user.model';

@Component({
  selector: 'app-company-admin-form',
  templateUrl: './company-admin-form.component.html',
  styleUrls: ['./company-admin-form.component.css']
})
export class CompanyAdminFormComponent {

  @Output() companyAdminAdded = new EventEmitter<null>();
  public companyAdminForm: FormGroup;

  constructor(private authService: AuthService) { 
    this.companyAdminForm = new FormGroup({
      email: new FormControl('', [Validators.required]),
      firstName: new FormControl('', [Validators.required]),
      lastName: new FormControl('', [Validators.required]),
      city: new FormControl(''),
      country: new FormControl(''),
      phoneNumber: new FormControl('', [Validators.required]),
      profession: new FormControl(''),
      companyInformation: new FormControl(''),
    });
  }

  saveAdmin(): void {
    let companyAdmin: User = {
      email: this.companyAdminForm.value.email || "",
      firstName: this.companyAdminForm.value.firstName || "",
      lastName: this.companyAdminForm.value.lastName || "",
      city: this.companyAdminForm.value.city || "",
      country: this.companyAdminForm.value.country || "",
      phoneNumber: this.companyAdminForm.value.phoneNumber || "",
      profession: this.companyAdminForm.value.profession || "",
      companyInformation: this.companyAdminForm.value.companyInformation || "",
    };

    this.authService.registerCompanyAdmin(companyAdmin).subscribe({
      next: () => {
        this.companyAdminAdded.emit()
      }
    });
  }
}
