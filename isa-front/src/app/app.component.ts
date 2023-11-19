import { Component } from '@angular/core';
import { Company } from './model/company.model';
import { CompanyService } from './service/company.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'isa-front';

  companies: Company[] = [];
  searchName: string = '';
  searchCity: string = '';
  searchCountry: string = '';


  constructor(private companyService: CompanyService) {}

  ngOnInit(): void {
    this.getAllCompanies();
  }

  getAllCompanies(): void {
    this.companyService.getAll()
      .subscribe((data) => {
        this.companies = data.content;
      });
  }

  searchCompanies(): void {
    if(this.searchName === null){this.searchName=''}
    if(this.searchCity === null){this.searchCity=''}
    if(this.searchCountry === null){this.searchCountry=''}
    this.companyService.searchCompanies(this.searchName, this.searchCity, this.searchCountry)
      .subscribe((data) => {
        this.companies = data;
      });
  }
}
