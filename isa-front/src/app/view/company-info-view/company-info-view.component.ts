import { Component, Input, OnInit } from '@angular/core';
import { Company } from "../../model/company.model";
import { Router } from "@angular/router";
import { MatDialog } from "@angular/material/dialog"
import { EquipmentSelectorComponent } from 'src/app/component/equipment-selector/equipment-selector.component';
import { Equipment } from 'src/app/model/equipment.model';
import { MessageService } from 'primeng/api';
import { InventoryService } from 'src/app/service/inventory/inventory.service';
import { InventoryItem } from 'src/app/model/inventory.model';
@Component({
  selector: 'app-company-info-view',
  templateUrl: './company-info-view.component.html',
  styleUrls: ['./company-info-view.component.css'],
  providers: [MessageService],
})
export class CompanyInfoViewComponent implements OnInit {
  company: Company;
  protected readonly Math = Math;
  selectedEquipment: Equipment[] = [];
  companyEquipment: InventoryItem[] = [];

  constructor(private router: Router, private dialog: MatDialog, private messageService: MessageService, private inventoryService: InventoryService) {
    const navigation = this.router.getCurrentNavigation();
    this.company = navigation?.extras.state?.['company'];
  }

  ngOnInit(): void {
    this.inventoryService.getInventoryForCompany(this.company.id).subscribe(result => {
      this.companyEquipment = result.content;
    })
  }

  addEquipmentToSelection(equipment: Equipment): void {
    this.selectedEquipment.push(equipment);
    this.messageService.add({ severity: "success", summary: "equipment added to selection" });
  }

  removeEquipmentFromSelection(equipment: Equipment): void {
    this.selectedEquipment.splice(this.selectedEquipment.indexOf(equipment), 1);
    this.messageService.add({ severity: "warn", summary: "equipment removed from selection" });
  }

  openEquipmentSelection(): void {
    if (this.selectedEquipment.length !== 0)
      this.dialog.open(EquipmentSelectorComponent, { data: this.selectedEquipment, width: '50%', height: '50%' });
    else
      this.messageService.add({ severity: "warn", summary: "Select items before proceeding" })
  }
}
