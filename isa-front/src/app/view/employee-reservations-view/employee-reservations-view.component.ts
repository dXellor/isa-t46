import { Component, OnInit } from '@angular/core';
import { Reservation } from 'src/app/model/reservation.model';
import { ReservationService } from 'src/app/service/reservation/reservation.service';
import { MatTableModule } from '@angular/material/table';
import {MessageService} from "primeng/api";

@Component({
  selector: 'app-employee-reservations-view',
  templateUrl: './employee-reservations-view.component.html',
  styleUrls: ['./employee-reservations-view.component.css'],
  providers: [MessageService]
})
export class EmployeeReservationsViewComponent implements OnInit {
  reservations: Reservation[] = [];
  displayedColumns: string[] = ['dateTime', 'duration', 'companyAdmin', 'cancel', 'confirm'];
  dataSource: Reservation[] = [];
  constructor(private reservationService: ReservationService, private messageService: MessageService) { }

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

  cancelReservation(reservationId: number): void{
    if(window.confirm(`Are you sure you want to cancel the reservation? You will receive penal points.`)){
      this.reservationService.cancelReservation(reservationId).subscribe(res => {
        window.alert(`Reservation canceled`);
        this.loadReservations();
      });
    }
  }

  confirmReservation(reservationId: number): void{
    if(window.confirm(`Are you sure you want to confirm this reservation?`)){
      this.reservationService.confirmReservation(reservationId).subscribe(res => {
        this.messageService.add({severity:'success', summary:'Success', detail:'Reservation confirmed successfully.'});
        this.loadReservations();
      });
    }
  }

  private loadReservations(): void{
    this.reservationService.getReservationsForEmployee().subscribe(result => {
      this.dataSource = result.content;
    })
  }

  protected readonly confirm = confirm;
}
