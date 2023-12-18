import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators, FormArray } from '@angular/forms';
import { Company } from 'src/app/model/company.model';
import { User } from 'src/app/model/user.model';
import { CompanyService } from 'src/app/service/company.service';
import { UserService } from 'src/app/service/user.service';
import { InventoryItem } from 'src/app/model/inventory.model';
import { InventoryService } from 'src/app/service/inventory/inventory.service';
import { MatDialog } from '@angular/material/dialog';
import { CompanyAdminAppointmentsComponent } from 'src/app/component/company-admin-appointments/company-admin-appointments.component';
import { CompanyInventoryManagementComponent } from 'src/app/component/company-inventory-management/company-inventory-management.component';

@Component({
  selector: 'app-company-profile-view',
  templateUrl: './company-profile-view.component.html',
  styleUrls: ['./company-profile-view.component.css'],
})

export class CompanyProfileViewComponent implements OnInit {
  public company: Company;
  public companyForm: FormGroup;
  public editMode = false;
  public user: User;


  constructor(private userService: UserService, private companyService: CompanyService, private formBuilder: FormBuilder, private inventoryService: InventoryService, private dialog: MatDialog) { }

  ngOnInit(): void {
    this.user = this.userService.getCurrentUser();

    this.companyService.getCompanyByAdminId(this.user.id).subscribe(company => {
      this.company = company;
      this.intitCompanyProfileForm()

    });
  }

  private intitCompanyProfileForm(): void {
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
      admins: this.formBuilder.array(this.company.admins || [])
    });
    this.companyForm.disable();
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

  isRatingField(controlName: string): boolean {
    return controlName === 'averageRating';
  }

  openAppointmentsSection(): void {
    this.dialog.open(CompanyAdminAppointmentsComponent);
  }

  openEquipmentSection(): void {
    this.dialog.open(CompanyInventoryManagementComponent, { data: this.company });
  }
}
