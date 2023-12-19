import { Component, Inject, OnInit } from '@angular/core';
import { trigger, style, animate, transition } from '@angular/animations'
import { MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Equipment } from 'src/app/model/equipment.model';
import { ReservationService } from 'src/app/service/reservation.service';
import { Reservation } from 'src/app/model/reservation.model';
import { KeyValue } from '@angular/common';

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
  chosenEquipment: Equipment[]=[];

  constructor(@Inject(MAT_DIALOG_DATA) data: any, private reservationService: ReservationService){
    this.companyId = data.companyId;
    this.chosenEquipment = data.chosenEquipment;
  }
  
  ngOnInit(): void {
  }

  loadTimeSlots(...args: any[]) {
    this.reservationService.getAvailableTimeSlots(this.companyId, this.selectedDate).subscribe({
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
  
}
