import { Component, Input } from '@angular/core';
import { Company } from "../../model/company.model";
import { Router } from "@angular/router";
import { MatDialog } from "@angular/material/dialog"
import { EquipmentSelectorComponent } from 'src/app/component/equipment-selector/equipment-selector.component';
import { Equipment } from 'src/app/model/equipment.model';
import { MessageService } from 'primeng/api';
@Component({
  selector: 'app-company-info-view',
  templateUrl: './company-info-view.component.html',
  styleUrls: ['./company-info-view.component.css'],
  providers: [MessageService],
})
export class CompanyInfoViewComponent {
  company: Company;
  protected readonly Math = Math;
  selectedEquipment: Equipment[] = [];

  constructor(private router: Router, private dialog: MatDialog, private messageService: MessageService) {
    const navigation = this.router.getCurrentNavigation();
    this.company = navigation?.extras.state?.['company'];
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
      this.dialog.open(EquipmentSelectorComponent, { data: this.selectedEquipment })
    else
      this.messageService.add({ severity: "warn", summary: "Select items before proceeding" })
  }
}
