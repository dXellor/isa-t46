import { Component, EventEmitter, Output } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { CompanyService } from '../../service/company.service'
import { Company } from '../../model/company.model';
import { Address } from '../../model/address.model';

@Component({
  selector: 'app-company-form',
  templateUrl: './company-form.component.html',
  styleUrls: ['./company-form.component.css']
})
export class CompanyFormComponent {

  @Output() companyAdded = new EventEmitter<null>();
  public companyForm: FormGroup;

  constructor(private companyService: CompanyService) {
    this.companyForm = new FormGroup({
      name: new FormControl('', [Validators.required]),
      description: new FormControl('', [Validators.required]),
      street: new FormControl('', [Validators.required]),
      city: new FormControl('', [Validators.required]),
      country: new FormControl('', [Validators.required]),
      zipCode: new FormControl('', [Validators.required]),
    });
  }

  saveCompany(): void {
    let companyAdress: Address = {
      street: this.companyForm.value.street || "",
      city: this.companyForm.value.country || "",
      country: this.companyForm.value.country || "",
      zipCode: this.companyForm.value.phoneNumber || "",
    }
    let company: Company = {
      name: this.companyForm.value.name || "",
      description: this.companyForm.value.description || "",
      address: companyAdress,
      startWork: "8:00:00",
      endWork: "16:00:00"
    };

    this.companyService.addCompany(company).subscribe({
      next: () => {
        this.companyAdded.emit()
      }
    });
  }
}
