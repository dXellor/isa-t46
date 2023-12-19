import { Component, Input, OnInit } from '@angular/core';
import { Company } from "../../model/company.model";
import { Router } from "@angular/router";
import { MatDialog } from "@angular/material/dialog"
import { EquipmentSelectorComponent } from 'src/app/component/equipment-selector/equipment-selector.component';
import { MessageService } from 'primeng/api';
import { InventoryService } from 'src/app/service/inventory/inventory.service';
import { InventoryItem } from 'src/app/model/inventory.model';
import { ReservationItem } from 'src/app/model/reservation/reservation-item.model';
import { Reservation } from 'src/app/model/reservation.model';
import { ReservationService } from 'src/app/service/reservation/reservation.service';
import { UserService } from 'src/app/service/user.service';
import { User } from 'src/app/model/user.model';
@Component({
  selector: 'app-company-info-view',
  templateUrl: './company-info-view.component.html',
  styleUrls: ['./company-info-view.component.css'],
  providers: [MessageService],
})
export class CompanyInfoViewComponent implements OnInit {
  company: Company;
  protected readonly Math = Math;
  selectedEquipment: ReservationItem[] = [];
  companyEquipment: InventoryItem[] = [];
  selectedCounts: { [id: number]: number } = {};
  userReservations: Reservation[] = [];
  currentUser: User;

  constructor(private router: Router, private dialog: MatDialog, private messageService: MessageService, private inventoryService: InventoryService, private reservationSerice: ReservationService, private userService: UserService) {
    const navigation = this.router.getCurrentNavigation();
    this.company = navigation?.extras.state?.['company'];
  }

  ngOnInit(): void {
    this.userService.getUser().subscribe((response) => { // making request again, because sometimes userService.getCurrentUser returns a null (happens when page refreshes)
      this.currentUser = response;
      if(this.currentUser){
        this.inventoryService.getInventoryForCompany(this.company.id).subscribe(result => {
          this.companyEquipment = result.content;
        });
        this.reservationSerice.getReservationsForEmployee().subscribe(result => {
          this.userReservations = result.content;
        })
      }
    });
  }

  addEquipmentToSelection(equipment: InventoryItem): void {
    const reservationItem: ReservationItem = {
      id: equipment.id,
      inventoryItem: equipment,
      count: this.selectedCounts[equipment.id]
    };

    this.selectedEquipment.push(reservationItem);
    this.messageService.add({ severity: "success", summary: "equipment added to selection" });
  }

  removeEquipmentFromSelection(equipment: InventoryItem): void {
    this.selectedEquipment.splice(this.selectedEquipment.indexOf(this.selectedEquipment.filter(e => e.inventoryItem === equipment)[0], 1));
    this.messageService.add({ severity: "warn", summary: "equipment removed from selection" });
    equipment.count += this.selectedCounts[equipment.id];
    this.selectedCounts[equipment.id] = 0;

  }

  openEquipmentSelection(): void {
    if (this.selectedEquipment.length !== 0)
      this.dialog.open(EquipmentSelectorComponent, { data: this.selectedEquipment, width: '50%', height: '50%' });
    else
      this.messageService.add({ severity: "warn", summary: "Select items before proceeding" })
  }

  isAlreadySelected(equipment: InventoryItem): boolean {
    return this.selectedEquipment.filter(e => e.inventoryItem == equipment).length != 0;
  }

  increaseEquipmentCount(equipment: InventoryItem): void {
    if (this.selectedCounts[equipment.id] === undefined) {
      this.selectedCounts[equipment.id] = 0;
    }
    this.selectedCounts[equipment.id]++;
    equipment.count--;
    this.selectedEquipment.filter(e => e.inventoryItem === equipment)[0].count = this.selectedCounts[equipment.id];
  }

  decraseEquipmentCount(equipment: InventoryItem): void {
    if (this.selectedCounts[equipment.id] === undefined) {
      this.selectedCounts[equipment.id] = 0;
    }
    this.selectedCounts[equipment.id]--;
    if (this.selectedCounts[equipment.id] < 0) {
      this.selectedCounts[equipment.id] = 0;
    }
    equipment.count++;
    this.selectedEquipment.filter(e => e.inventoryItem === equipment)[0].count = this.selectedCounts[equipment.id];
  }
}
