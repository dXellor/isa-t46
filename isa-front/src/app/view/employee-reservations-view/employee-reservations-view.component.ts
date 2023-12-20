import { Component, OnInit } from '@angular/core';
import { Reservation } from 'src/app/model/reservation.model';
import { ReservationService } from 'src/app/service/reservation/reservation.service';
import { MatTableModule } from '@angular/material/table';

@Component({
  selector: 'app-employee-reservations-view',
  templateUrl: './employee-reservations-view.component.html',
  styleUrls: ['./employee-reservations-view.component.css'],
})
export class EmployeeReservationsViewComponent implements OnInit {
  reservations: Reservation[] = [];
  displayedColumns: string[] = ['dateTime', 'duration', 'companyAdmin'];
  dataSource: Reservation[] = [];
  constructor(private reservationService: ReservationService) { }
  ngOnInit(): void {
    this.reservationService.getReservationsForEmployee().subscribe(result => {
      this.dataSource = result.content;
    })
  }

  formatReservation(reservation: Reservation): string {
    const dateTime = new Date(reservation.dateTime);
    const date = `${dateTime.getDate()}.${dateTime.getMonth() + 1}.${dateTime.getFullYear()}`;
    const time = `${dateTime.getHours()}:${String(dateTime.getMinutes()).padStart(2, '0')}`;
    const duration = reservation.duration;
    const firstName = reservation.companyAdmin.firstName;
    const lastName = reservation.companyAdmin.lastName;

    return `${date} - ${time} - ${duration} - ${firstName} - ${lastName}`;
  }

  formatDate(dateT: Date): string {
    const dateTime = new Date(dateT);
    const date = `${dateTime.getDate()}.${dateTime.getMonth() + 1}.${dateTime.getFullYear()}`;
    const time = `${dateTime.getHours()}:${String(dateTime.getMinutes()).padStart(2, '0')}`;
    return `${date} - ${time}`
  }
}
