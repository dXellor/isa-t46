import {Component, OnInit} from '@angular/core';
import {Reservation} from "../../model/reservation/reservation.model";
import {ReservationService} from "../../service/reservation/reservation.service";

@Component({
  selector: 'app-company-admin-reservations-view',
  templateUrl: './company-admin-reservations-view.component.html',
  styleUrls: ['./company-admin-reservations-view.component.css']
})
export class CompanyAdminReservationsViewComponent implements OnInit{
  reservations: Reservation[] = [];
  displayedColumns: string[] = ['dateTime', 'duration', 'companyAdmin', 'start'];
  dataSource: Reservation[] = [];

  constructor(private reservationService: ReservationService) { }
  ngOnInit() {
    this.loadReservations();
  }

  loadReservations(){
    this.reservationService.getConfirmedReservations().subscribe(result => {
      this.dataSource = result.content;
    });
  }

  startDelivery(reservationId: number){
    this.reservationService.startDelivery(reservationId).subscribe(result => {
      alert("delivery ended");
    });
  }

  formatDate(dateT: Date): string {
    const dateTime = new Date(dateT);
    const date = `${dateTime.getDate()}.${dateTime.getMonth() + 1}.${dateTime.getFullYear()}`;
    const time = `${dateTime.getHours()}:${String(dateTime.getMinutes()).padStart(2, '0')}`;
    return `${date} - ${time}`
  }
}
