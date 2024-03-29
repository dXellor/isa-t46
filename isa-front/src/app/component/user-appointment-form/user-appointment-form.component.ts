import { Component, Inject, OnInit } from '@angular/core';
import { trigger, style, animate, transition } from '@angular/animations'
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { Equipment } from 'src/app/model/equipment.model';
import { ReservationService } from 'src/app/service/reservation.service';
import { Reservation } from 'src/app/model/reservation.model';
import { ReservationItem } from 'src/app/model/reservation/reservation-item.model';
import { Company } from 'src/app/model/company.model';
import { OutOfOrderReservation } from 'src/app/model/out-of-order-reservation-model';
import { User } from 'src/app/model/user.model';
import { UserService } from 'src/app/service/user.service';

@Component({
  selector: 'app-user-appointment-form',
  templateUrl: './user-appointment-form.component.html',
  styleUrls: ['./user-appointment-form.component.css'],
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
export class UserAppointmentFormComponent implements OnInit {

  selectedDate : Date; 
  reservations: Reservation[]=[];
  companyId: number = 1; 
  userId : number = 1;
  chosenEquipment: ReservationItem[]=[];
  company : Company;
  outOfOrderReservation : OutOfOrderReservation={} as OutOfOrderReservation;
  loggedUser: User;

  constructor(@Inject(MAT_DIALOG_DATA) data: any, private reservationService: ReservationService, private dialogRef: MatDialogRef<UserAppointmentFormComponent>, private userService: UserService){
    this.chosenEquipment = data.chosenEquipment;
    this.company = data.companyInfo;
  }
  
  ngOnInit(): void {
    this.loggedUser = this.userService.getCurrentUser();
  }

  loadTimeSlots(...args: any[]) {
    this.reservationService.getAvailableTimeSlots(this.company.id, this.selectedDate).subscribe({
      next: (response: Reservation[]) => {
        this.reservations = response;
      },
      error: (error) => {
        console.error('Error:', error);
      },
      complete: () => {
        console.log('Request complete.');
      }
    });
  }

  createReservation(reservation: Reservation) {
    if(this.loggedUser.penalPoints > 2){
      alert('Reservation not possible! You have 3 or more penal points.');
      this.dialogRef.close({ note: 'Reservation not possible! You have 3 or more penal points.' });
      return;
    }
    reservation.company = this.company;
    this.outOfOrderReservation.reservation = reservation;
    this.outOfOrderReservation.reservation_items = this.chosenEquipment;
    this.reservationService.createReservationWithOutOfOrderAppointment(this.outOfOrderReservation).subscribe(
      (createdReservation: Reservation) => {
        window.alert('Reservation created successfully!');
        this.dialogRef.close({ note: 'Reservation created successfully!' });
      },
      
    );
  }
}


