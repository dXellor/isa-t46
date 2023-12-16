import { Component, Inject, OnInit } from '@angular/core';
import { trigger, transition, animate, style } from '@angular/animations'
import { Equipment } from 'src/app/model/equipment.model';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MatDialog } from '@angular/material/dialog';
import { UserAppointmentFormComponent } from '../user-appointment-form/user-appointment-form.component';

@Component({
  selector: 'app-equipment-selector',
  templateUrl: './equipment-selector.component.html',
  styleUrls: ['./equipment-selector.component.css'],
  animations: [
    trigger('slideInOut', [
      transition(':enter', [
        style({ transform: 'translateX(100%)' }),
        animate('200ms ease-in', style({ transform: 'translateX(0%)' }))
      ]),
      transition(':leave', [
        animate('200ms ease-out', style({ transform: 'translateX(100%)' }))
      ])
    ])
  ]
})
export class EquipmentSelectorComponent implements OnInit {
  selectedEquipment: Equipment[] = [];
  predefinedAppointments: Equipment[] = [];

  constructor(@Inject(MAT_DIALOG_DATA) public companyEquipment: Equipment[], private dialog: MatDialog) { }

  ngOnInit(): void {

  }

  createNewAppointmentDate(): void {
    this.dialog.open(UserAppointmentFormComponent, { data: this.predefinedAppointments })
  }

}
