import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl, FormBuilder, Validators, FormArray } from '@angular/forms';
import { Company } from 'src/app/model/company.model';
import { User } from 'src/app/model/user.model';
import { CompanyService } from 'src/app/service/company.service';
import { UserService } from 'src/app/service/user.service';

@Component({
  selector: 'app-company-profile-view',
  templateUrl: './company-profile-view.component.html',
  styleUrls: ['./company-profile-view.component.css']
})
export class CompanyProfileViewComponent implements OnInit {
  public company: Company;
  public companyForm: FormGroup;
  public editMode = false;
  public user: User;

  constructor(private userService: UserService, private companyService: CompanyService, private formBuilder: FormBuilder) { }

  ngOnInit(): void {
    this.userService.getUser().subscribe(user => {
      this.user = user;
    });
    this.companyService.getCompanyByAdminId(3).subscribe(company => {
      this.company = company;
      console.log(company);
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
    console.log(this.companyForm.value);
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
}
