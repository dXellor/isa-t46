import { Component } from '@angular/core';
import { Reservation } from 'src/app/model/reservation.model';
import { ReservationService } from 'src/app/service/reservation.service';

@Component({
  selector: 'app-user-completed-reservations',
  templateUrl: './user-completed-reservations.component.html',
  styleUrls: ['./user-completed-reservations.component.css']
})
export class UserCompletedReservationsComponent {
  reservations: Reservation[] = [];
  displayedColumns: string[] = ['dateTime', 'duration', 'companyAdmin'];
  dataSource: Reservation[] = [];
  sortByDuration: boolean = false;
  sortByDateTime: boolean = false;
  selectedSortOrder: string = 'asc';

  constructor(private reservationService: ReservationService) { }
  
  ngOnInit(): void {
    this.loadReservations();
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

  private loadReservations(): void{
    this.reservationService.getCompletedReservationsForUser().subscribe(result => {
      this.dataSource = result;
    })
  }

  sortReservations(): void {
    this.reservationService.sortReservationsByDurationAndDate(
      this.sortByDuration,
      this.sortByDateTime,
      this.selectedSortOrder
    ).subscribe(sortedReservations => this.dataSource = sortedReservations);
  }

}


