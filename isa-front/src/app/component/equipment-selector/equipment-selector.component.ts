import { Component, Inject, OnInit } from '@angular/core';
import { trigger, transition, animate, style } from '@angular/animations'
import { Equipment } from 'src/app/model/equipment.model';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MatDialog } from '@angular/material/dialog';
import { UserAppointmentFormComponent } from '../user-appointment-form/user-appointment-form.component';
import { ReservationService } from 'src/app/service/reservation/reservation.service';
import { Reservation } from 'src/app/model/reservation/reservation.model';
import { UserService } from 'src/app/service/user.service';
import { User } from 'src/app/model/user.model';
import { Observable } from 'rxjs/internal/Observable';
import { map } from 'rxjs/internal/operators/map';

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
  predefinedAppointments: Reservation[] = [];

  constructor(@Inject(MAT_DIALOG_DATA) public companyEquipment: Equipment[], private dialog: MatDialog, private reservationService: ReservationService, private userService: UserService) { }

  ngOnInit(): void {
    this.reservationService.getAllPaged().subscribe(result => {
      this.predefinedAppointments = result.content;
    })
  }

  createNewAppointmentDate(): void {
    this.dialog.open(UserAppointmentFormComponent, { data: this.predefinedAppointments })
  }

  formatAppointment(appointment: Reservation): string {
    const dateTime = new Date(appointment.dateTime);
    const date = `${dateTime.getDate()}.${dateTime.getMonth() + 1}.${dateTime.getFullYear()}`;
    const time = `${dateTime.getHours()}:${String(dateTime.getMinutes()).padStart(2, '0')}`;
    const duration = appointment.duration;
    const firstName = appointment.companyAdmin.firstName;
    const lastName = appointment.companyAdmin.lastName;

    return `${date} - ${time} - ${duration} - ${firstName} - ${lastName}`;
  }
}
