import { Component, Inject, OnInit } from '@angular/core';
import { trigger, style, animate, transition } from '@angular/animations'
import { MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Equipment } from 'src/app/model/equipment.model';
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

  constructor(@Inject(MAT_DIALOG_DATA) predefindedAppointments: Equipment[]) { }

  ngOnInit(): void {

  }
}
