import { Component, Inject, OnInit } from '@angular/core';
import { resetFakeAsyncZone } from '@angular/core/testing';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Company } from 'src/app/model/company.model';
import { Equipment } from 'src/app/model/equipment.model';
import { InventoryItem } from 'src/app/model/inventory.model';
import { EquipmentService } from 'src/app/service/equipment.service';
import { InventoryService } from 'src/app/service/inventory/inventory.service';

@Component({
  selector: 'app-company-inventory-management',
  templateUrl: './company-inventory-management.component.html',
  styleUrls: ['./company-inventory-management.component.css']
})
export class CompanyInventoryManagementComponent implements OnInit {
  companyEquipment: InventoryItem[] = [];
  avaliableEquipment: Equipment[] = [];
  input: number = 0;

  constructor(@Inject(MAT_DIALOG_DATA) public company: Company, private inventoryService: InventoryService, private equipmentService: EquipmentService) { }
  ngOnInit(): void {
    this.loadCompanyEquipment();
  }

  loadCompanyEquipment(): void {
    this.inventoryService.getInventoryForCompany(this.company.id).subscribe(result => {
      this.companyEquipment = result.content;
      this.loadAvaliableEquipment();
    })
  }

  loadAvaliableEquipment(): void {
    this.equipmentService.getAll().subscribe(result => {
      this.avaliableEquipment = result.content.filter(equipment =>
        !this.companyEquipment.some(companyEquipment => companyEquipment.equipment.id === equipment.id)
      );
    })
  }

  removeEquipment(index: number): void {
    this.companyEquipment.splice(index, 1);
  }

  addEquipmentToCompany(equipment: Equipment): void {
    let inventoryItem: InventoryItem = {
      equipment: equipment,
      company: this.company,
      count: 5
    }
    console.log(inventoryItem);

    this.inventoryService.addInventory(inventoryItem).subscribe(result => {

    })
  }
}
