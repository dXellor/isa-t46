import { Component } from '@angular/core';
import { Company } from '../../model/company.model';
import { CompanyService } from '../../service/company.service';
import { PagedResult } from '../../model/paged-result.model';
import { UserService } from '../../service/user.service';
import { User } from '../../model/user.model';
import {MessageService} from "primeng/api";

@Component({
  selector: 'app-company-managing-view',
  templateUrl: './company-managing-view.component.html',
  styleUrls: ['./company-managing-view.component.css'],
  providers: [MessageService]
})
export class CompanyManagingViewComponent {
  public companies: Company[];

  public selectedCompany: Company;
  public allCompanyAdmins: User[];
  public availableAdmins: User[]; // admins that are not in current company // needs fixing
  public currentCompanyAdmins: User[] | undefined;

  public showForm: boolean;
  public showCompanyAdmins: boolean;

  searchName: string = '';
  searchCity: string = '';
  searchCountry: string = '';

  avgRatingMin: null;
  avgRatingMax: null;

  constructor(private companyService: CompanyService, private userService: UserService, private messageService: MessageService) { }

  ngOnInit(): void {
      this.getAllCompanies();
      this.getAllAdmins();
  }

  getAllCompanies(): void {
    this.companyService.getAll().subscribe({
      next: (response: PagedResult<Company>) => {
        this.companies = response.content;
      }
    });
  }

  getAllAdmins(): void {
    this.userService.getAllCompanyAdmins().subscribe({
      next: (response: PagedResult<User>) => {
        this.allCompanyAdmins = response.content;
      }
    });
  }

  addNewCompany(): void {
    this.showForm = true;
    this.showCompanyAdmins = false;
  }

  companyAdded(): void {
    this.getAllCompanies();
    this.showForm = false;
  }

  openManageAdmins(company: Company): void {
    this.selectedCompany = company;
    this.currentCompanyAdmins = company.admins;
    this.availableAdmins = this.allCompanyAdmins;

    if(this.currentCompanyAdmins){
      let ids = this.currentCompanyAdmins.map(cca => cca.id);
      this.availableAdmins = this.availableAdmins.filter((ca) => ids.indexOf(ca.id) == -1);
    }

    this.showForm = false;
    this.showCompanyAdmins = true;
  }

  cancelUpdate(): void {
    this.showCompanyAdmins = false;
  }

  updateCompany(): void {
    this.selectedCompany.admins = this.currentCompanyAdmins;

    this.companyService.updateCompany(this.selectedCompany).subscribe({
      next: (response: Company) => {
        this.messageService.add({
          severity: 'success',
          summary: 'Successfully updated',
        });
      }
    });
    this.showCompanyAdmins = false;
  }

  addAdmin(companyAdmin: User): void {
    this.currentCompanyAdmins?.push(companyAdmin);
    this.availableAdmins = this.availableAdmins.filter((user) => user.id != companyAdmin.id);
  }

  removeAdmin(companyAdmin: User): void {
    this.currentCompanyAdmins = this.currentCompanyAdmins?.filter((user) => user.id != companyAdmin.id);
    this.availableAdmins.push(companyAdmin);
  }

  searchCompanies(): void {
    if(this.searchName === null){this.searchName=''}
    if(this.searchCity === null){this.searchCity=''}
    if(this.searchCountry === null){this.searchCountry=''}
    this.companyService.searchCompanies(this.searchName, this.searchCity, this.searchCountry).subscribe({
      next: (response: PagedResult<Company>) => {
        this.companies = response.content;
      }
    });
  }

  filterCompanies() : void {
    this.companyService.filterCompanies(this.avgRatingMin, this.avgRatingMax).subscribe({
      next: (response: PagedResult<Company>) => {
        this.companies = response.content;
      }
    });
  }
}
