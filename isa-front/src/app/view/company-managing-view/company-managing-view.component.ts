import { Component } from '@angular/core';
import { Company } from '../../model/company.model';
import { CompanyService } from '../../service/company.service';
import { PagedResult } from '../../model/paged-result.model';

@Component({
  selector: 'app-company-managing-view',
  templateUrl: './company-managing-view.component.html',
  styleUrls: ['./company-managing-view.component.css']
})
export class CompanyManagingViewComponent {
  public companies: Company[];
  public showForm: boolean;

  constructor(private companyService: CompanyService) { }

  ngOnInit(): void {
      this.getAllCompanies();
  }

  getAllCompanies(): void {
    this.companyService.getAll().subscribe({
      next: (response: PagedResult<Company>) => {
        this.companies = response.content;
      }
    });
  }

  addNewCompany(): void {
    this.showForm = true;
  }

  companyAdded(): void {
    console.log("company addeddd");
    this.getAllCompanies();
    this.showForm = false;
  }
}
