import { Component, EventEmitter, Output } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { User } from 'src/app/model/user.model';
import { AuthService } from 'src/app/service/auth.service';

@Component({
  selector: 'app-system-admin-form',
  templateUrl: './system-admin-form.component.html',
  styleUrls: ['./system-admin-form.component.css']
})
export class SystemAdminFormComponent {
  @Output() systemAdminAdded = new EventEmitter<null>();
  public systemAdminForm: FormGroup;

  constructor(private authService: AuthService) { 
    this.systemAdminForm = new FormGroup({
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
    let systemAdmin: User = {
      email: this.systemAdminForm.value.email || "",
      firstName: this.systemAdminForm.value.firstName || "",
      lastName: this.systemAdminForm.value.lastName || "",
      city: this.systemAdminForm.value.city || "",
      country: this.systemAdminForm.value.country || "",
      phoneNumber: this.systemAdminForm.value.phoneNumber || "",
      profession: this.systemAdminForm.value.profession || "",
      companyInformation: this.systemAdminForm.value.companyInformation || "",
    };

    this.authService.registerSystemAdmin(systemAdmin).subscribe({
      next: () => {
        this.systemAdminAdded.emit()
      }
    });
  }
}
