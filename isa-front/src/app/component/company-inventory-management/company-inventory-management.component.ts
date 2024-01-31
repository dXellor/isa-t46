import { Component, Inject, OnInit } from '@angular/core';
import { FormControl } from '@angular/forms';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MessageService } from 'primeng/api';
import { Company } from 'src/app/model/company.model';
import { Equipment } from 'src/app/model/equipment.model';
import { InventoryItem } from 'src/app/model/inventory.model';
import { ReservationItem } from 'src/app/model/reservation/reservation-item.model';
import { EquipmentService } from 'src/app/service/equipment.service';
import { InventoryService } from 'src/app/service/inventory/inventory.service';
import { ReservationService } from 'src/app/service/reservation/reservation.service';

@Component({
  selector: 'app-company-inventory-management',
  templateUrl: './company-inventory-management.component.html',
  styleUrls: ['./company-inventory-management.component.css'],
  providers: [MessageService]
})
export class CompanyInventoryManagementComponent implements OnInit {
  companyEquipment: { inventoryItem: InventoryItem, inventoryCount: number }[] = [];
  avaliableEquipment: { equipment: Equipment, inventoryCount: number }[] = [];
  inventoryCount: number = 0;
  reservationItems: ReservationItem[] = [];
  displayedColumnsCompany: string[] = ['name', 'count', 'remove', 'edit', 'countInput'];
  displayedColumnsAvailable: string[] = ['name', 'add', 'countInput'];
  filteredEquipment: Equipment[];
  nameControl = new FormControl(''); // control for the name input field


  constructor(@Inject(MAT_DIALOG_DATA) public company: Company, private inventoryService: InventoryService, private equipmentService: EquipmentService, private messageService: MessageService, private reservationService: ReservationService) { }

  ngOnInit(): void {
    this.loadCompanyEquipment();
    this.loadReservationItems();

  }

  loadReservationItems(): void {
    this.reservationService.getReservationItems().subscribe(result => {
      this.reservationItems = result.content;

    })
  }

  isReservedItem(inventoryItem: InventoryItem): boolean {
    console.log(this.reservationItems.some(re => re.inventoryItem.id === inventoryItem.id));

    return this.reservationItems.some(re => re.inventoryItem.id === inventoryItem.id);
  }

  loadCompanyEquipment(): void {
    this.inventoryService.getInventoryForCompany(this.company.id).subscribe(result => {
      this.companyEquipment = result.content.map(item => ({ inventoryItem: item, inventoryCount: item.count }));
      this.filterEquipment();
      this.loadAvaliableEquipment();
    })
  }

  loadAvaliableEquipment(): void {
    this.equipmentService.getAll().subscribe(result => {
      this.avaliableEquipment = result.content.map(item => ({ equipment: item, inventoryCount: 1 }));
      this.avaliableEquipment = this.avaliableEquipment.filter(availableEquipment =>
        !this.companyEquipment.some(companyEquipment => companyEquipment.inventoryItem.equipment.id === availableEquipment.equipment.id)
      );
    })
  }

  addEquipmentToCompany(equipment: { equipment: Equipment, inventoryCount: number }): void {
    let inventoryItem: InventoryItem = {
      equipment: equipment.equipment,
      company: this.company,
      count: equipment.inventoryCount
    };
    this.inventoryService.addInventory(inventoryItem).subscribe(result => {
      this.companyEquipment.push({ inventoryItem: result, inventoryCount: equipment.inventoryCount });
      this.companyEquipment = [...this.companyEquipment];
      const index = this.avaliableEquipment.findIndex(e => e.equipment.id === equipment.equipment.id);
      if (index !== -1) {
        this.avaliableEquipment.splice(index, 1);
        this.avaliableEquipment = [... this.avaliableEquipment];
      }
      this.messageService.add({ severity: "success", summary: "Equipment successfully added to company inventory." });
    });
  }

  removeEquipment(index: number, inventoryItem: InventoryItem): void {
    if (this.isReservedItem(inventoryItem))
      this.messageService.add({ severity: "warn", summary: "Items is currently reseved so it can't be removed from inventory" });

    this.companyEquipment.splice(index, 1);
    this.companyEquipment = [...this.companyEquipment];
    this.avaliableEquipment.push({ equipment: inventoryItem.equipment, inventoryCount: 1 });
    this.avaliableEquipment = [... this.avaliableEquipment];
    this.inventoryService.removeInventoryItem(inventoryItem.id).subscribe(result => {
      this.messageService.add({ severity: "success", summary: "Equipment successfully removed from company inventory." });
    })
  }
  editEquipment(inventoryItem: { inventoryItem: InventoryItem, inventoryCount: number }): void {
    inventoryItem.inventoryItem.count = inventoryItem.inventoryCount;
    this.inventoryService.updateInventoryItem(inventoryItem.inventoryItem).subscribe(result => {
      this.messageService.add({ severity: "success", summary: "Equipment updated successfully." });
    })
  }

  filterEquipment(): void {
    const name = this.nameControl.value.toLowerCase();
    this.companyEquipment = this.companyEquipment.filter(equipment =>
      equipment.inventoryItem.equipment.name.toLowerCase().includes(name)
    );
  }

}
