import {Component, OnInit} from '@angular/core';
import {CompanyService} from "../../service/company.service";
import {Company} from "../../model/company.model";
import {roleRoutes} from "../../model/user.model";
import {Router} from "@angular/router";

@Component({
  selector: 'app-user-home-view',
  templateUrl: './user-home-view.component.html',
  styleUrls: ['./user-home-view.component.css']
})
export class UserHomeViewComponent implements OnInit {

  companies: Company[] = [];
  constructor(private companyService: CompanyService, private router: Router) {
  }
  ngOnInit(): void {
    this.companyService.getAll().subscribe(result => {
      this.companies = result.content;
    })
  }

  showCompanyProfile(company: Company): void {
    this.router.navigate(['company-info'], { state: { company: company } });
  }
}
