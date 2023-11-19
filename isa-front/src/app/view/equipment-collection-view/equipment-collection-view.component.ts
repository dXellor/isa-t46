import { Component } from '@angular/core';
import { EquipmentService } from '../../service/equipment.service';
import { Equipment } from '../../model/equipment.model';
import { FormControl, FormGroup } from '@angular/forms';
import { PagedResult } from '../../model/paged-result.model';
import { EquipmentFilterCriteria } from '../../model/equipment-filter-criteria.model';
import { CompanyService } from '../../service/company.service';
import { Company } from '../../model/company.model';

@Component({
  selector: 'app-equipment-collection-view',
  templateUrl: './equipment-collection-view.component.html',
  styleUrls: ['./equipment-collection-view.component.css']
})
export class EquipmentCollectionViewComponent {

  public equipment: Equipment[];
  public filterForm: FormGroup;
  public possibleTypes: string[];
  public companiesWithEquipment: Company[];
  public openCompanyPopup: boolean;

  constructor(private equipmentService: EquipmentService, private companyService: CompanyService) {
    this.filterForm = new FormGroup({
      name: new FormControl(''),
      priceMin: new FormControl(0),
      priceMax: new FormControl(),
      type: new FormControl(''),
    });
    this.openCompanyPopup = false;
  }

  ngOnInit(): void {
      this.getAllEquipment();
  }

  getAllEquipment(): void {
    this.equipmentService.getAll().subscribe({
      next: (response: PagedResult<Equipment>) => {
        this.equipment = response.content;
        this.extractTypes(); // needs better way, bad
      }
    });
  }

  filterEquipment(): void {
    let filter: EquipmentFilterCriteria = {
      name: this.filterForm.value.name || "",
      priceMin: this.filterForm.value.priceMin,
      priceMax: this.filterForm.value.priceMax,
      type: this.filterForm.value.type,
    }

    console.log(filter);

    this.equipmentService.getFiltered(filter).subscribe({
      next: (response: PagedResult<Equipment>) => {
        this.equipment = response.content;
      }
    });
  }

  extractTypes(): void {
    this.possibleTypes = [...new Set( this.equipment.map(e => e.type)) ];
    console.log("POSSIBLE");
    console.log(this.possibleTypes);
  }

  openCompanyList(e: Equipment): void {
    this.companyService.getByEquipment(e.id || 0).subscribe({
      next: (response: PagedResult<Company>) => {
        this.companiesWithEquipment = response.content;
        this.openCompanyPopup = true;
      }
    });
  }

  closePopup(): void {
    this.openCompanyPopup = false;
  }
}
