import { Component } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { Router } from '@angular/router';
import { Company } from 'src/app/model/company.model';
import { EquipmentFilterCriteria } from 'src/app/model/equipment-filter-criteria.model';
import { Equipment } from 'src/app/model/equipment.model';
import { PagedResult } from 'src/app/model/paged-result.model';
import { CompanyService } from 'src/app/service/company.service';
import { EquipmentService } from 'src/app/service/equipment.service';

@Component({
  selector: 'app-visitor-home-view',
  templateUrl: './visitor-home-view.component.html',
  styleUrls: ['./visitor-home-view.component.css']
})
export class VisitorHomeViewComponent {
  companies: Company[] = [];
  equipment: Equipment[] = [];

  public companyFilterForm: FormGroup;
  public equipmentFilterForm: FormGroup;

  
  public equipmentPossibleTypes: string[];

  view: string;

  constructor(private companyService: CompanyService, private equipmentService: EquipmentService, private router: Router) {
    this.view = 'company';

    this.equipmentFilterForm = new FormGroup({
      name: new FormControl(''),
      priceMin: new FormControl(0),
      priceMax: new FormControl(),
      type: new FormControl(''),
    });

    this.companyFilterForm = new FormGroup({
      name: new FormControl(''),
      city: new FormControl(''),
      country: new FormControl(''),
      avgRatingMin: new FormControl(0),
      avgRatingMax: new FormControl(5)
    });
  }

  ngOnInit(): void {
    this.getAllCompanies();
  }

  getAllCompanies() {
    this.companyService.getAll().subscribe({
      next: (response: PagedResult<Company>) => {
        this.companies = response.content;
      }
    });
  }

  getAllEquipment() {
    this.equipmentService.getAll().subscribe({
      next: (response: PagedResult<Equipment>) => {
        this.equipment = response.content;
        this.extractEquipmentTypes();
      }
    });
  }

  showCompanyProfile(company: Company): void {
    this.router.navigate(['company-info'], { state: { company: company } });
  }

  filterEquipment() {
    let filter: EquipmentFilterCriteria = {
      name: this.equipmentFilterForm.value.name || "",
      priceMin: this.equipmentFilterForm.value.priceMin,
      priceMax: this.equipmentFilterForm.value.priceMax,
      type: this.equipmentFilterForm.value.type,
    }
    
    this.equipmentService.getFiltered(filter).subscribe({
      next: (response: PagedResult<Equipment>) => {
        this.equipment = response.content;
      }
    });
  }

  searchCompanies(): void {
    let companySearch = {
      name: this.companyFilterForm.value.name || '',
      city: this.companyFilterForm.value.city || '',
      country: this.companyFilterForm.value.country || ''
    }

    this.companyService.searchCompanies(companySearch.name, companySearch.city, companySearch.country).subscribe({
      next: (response: PagedResult<Company>) => {
        this.companies = response.content;
      }
    });
  }

  filterCompanies() : void {
    let companyFilter = {
      avgRatingMin: this.companyFilterForm.value.avgRatingMin || 0,
      avgRatingMax: this.companyFilterForm.value.avgRatingMax || 5
    }

    this.companyService.filterCompanies(companyFilter.avgRatingMin, companyFilter.avgRatingMax).subscribe({
      next: (response: PagedResult<Company>) => {
        this.companies = response.content;
      }
    });
  }

  extractEquipmentTypes(): void {
    this.equipmentPossibleTypes = [...new Set(this.equipment.map(e => e.type))];
  }

  changeView(view: string): void {
    this.view = view;
    switch(view) {
      case 'company': this.getAllCompanies(); break;
      case 'equipment': this.getAllEquipment(); break;
    }
  }
}
