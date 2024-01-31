import { Component, Inject, OnInit } from '@angular/core';
import { trigger, transition, animate, style } from '@angular/animations'
import { Equipment } from 'src/app/model/equipment.model';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MatDialog } from '@angular/material/dialog';
import { UserAppointmentFormComponent } from '../user-appointment-form/user-appointment-form.component';
import { ReservationService } from 'src/app/service/reservation/reservation.service';
import { Reservation } from 'src/app/model/reservation/reservation.model';
import { ReservationRequest } from 'src/app/model/reservation/reservation-request.model';
import { ReservationItem } from 'src/app/model/reservation/reservation-item.model';
import {ConfirmationService, MessageService} from 'primeng/api';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Company } from 'src/app/model/company.model';
import { UserService } from 'src/app/service/user.service';
import { User } from 'src/app/model/user.model';

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
  ],
  providers: [MessageService, ConfirmationService],
})
export class EquipmentSelectorComponent implements OnInit {
  predefinedAppointments: Reservation[] = [];
  reservationRequest: ReservationRequest = {} as ReservationRequest;
  selectedAppointment: Reservation = {} as Reservation;
  reservationNote: string = "";
  noteForm: FormGroup;
  loggedUser: User;

  constructor(@Inject(MAT_DIALOG_DATA) public chosenEquipment: ReservationItem[], private dialog: MatDialog, private reservationService: ReservationService, private messageService: MessageService, private fb: FormBuilder, private userService: UserService) {
    this.noteForm = fb.group({
      note: ["", []],
    });
  }

  ngOnInit(): void {
    this.reservationService.getAvailableAppointments(this.chosenEquipment[0].inventoryItem.company.id).subscribe(result => {
      this.predefinedAppointments = result;
    })
    this.loggedUser = this.userService.getCurrentUser();
  }

  createNewAppointmentDate(): void {

    this.dialog.open(UserAppointmentFormComponent, {

      data: {
        predefinedAppointments: this.predefinedAppointments,
        chosenEquipment: this.chosenEquipment,
        companyInfo : this.predefinedAppointments[0].company
      }
    });
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

  createReservation(): void {
    if(this.loggedUser.penalPoints > 2){
      alert('Reservation not possible! You have 3 or more penal points.');
      return;
    }

    for (let reservationItem of this.chosenEquipment) {
      reservationItem.id = this.selectedAppointment.id
    }
    this.reservationRequest.reservation_id = this.selectedAppointment.id;
    if (this.reservationRequest.reservation_id) {
      this.reservationRequest.reservation_items = this.chosenEquipment;
      this.reservationRequest.note = this.noteForm.value["note"];

      this.reservationService.addReservation(this.reservationRequest).subscribe(result => {
        this.messageService.add({severity:'success', summary:'Success', detail:'You have successfully made a reservation. Check your email for details.'});
        this.dialog.closeAll();
      }, error => {
        this.messageService.add({severity:'error', summary:'Success', detail:'There was an error while receiving you request, please try again.'});
        // window.location.reload();
      });
    } else {
      this.messageService.add({severity:'warn', summary:'Success', detail:'Select appointment before you confirm reservation.'});
    }
  }
}
