import { Component, OnInit } from '@angular/core';
import { Reservation } from 'src/app/model/reservation.model';
import { ReservationService } from 'src/app/service/reservation/reservation.service';
import { MatTableModule } from '@angular/material/table';
import {ConfirmationService, MessageService} from "primeng/api";

@Component({
  selector: 'app-employee-reservations-view',
  templateUrl: './employee-reservations-view.component.html',
  styleUrls: ['./employee-reservations-view.component.css'],
  providers: [MessageService, ConfirmationService]
})
export class EmployeeReservationsViewComponent implements OnInit {
  reservations: Reservation[] = [];
  displayedColumns: string[] = ['dateTime', 'duration', 'companyAdmin', 'cancel', 'confirm'];
  dataSource: Reservation[] = [];
  constructor(private reservationService: ReservationService, private messageService: MessageService, private confirmationService: ConfirmationService) { }

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
    this.confirmationService.confirm({
      message: 'Are you sure you want to cancel the reservation? You will receive penal points.',
      header: 'Confirmation',
      rejectButtonStyleClass: 'p-button-text',
      acceptButtonStyleClass: 'p-danger p-button-text',
      accept:() => {
      this.reservationService.cancelReservation(reservationId).subscribe(res => {
        this.messageService.add({severity:'success', summary:'Success', detail:'Reservation canceled.'})
        this.loadReservations();
      });
      },
    });
  }

  confirmReservation(reservationId: number): void{
    this.confirmationService.confirm({
      message: 'Are you sure you want to confirm this reservation?',
      header: 'Confirmation',
      rejectButtonStyleClass: 'p-button-text',
      acceptButtonStyleClass: 'p-danger p-button-text',
      accept:() => {
        this.reservationService.confirmReservation(reservationId).subscribe(res => {
          this.messageService.add({severity:'success', summary:'Success', detail:'Reservation confirmed successfully.'});
          this.loadReservations();
        });
      },
    });
  }

  private loadReservations(): void{
    this.reservationService.getReservationsForEmployee().subscribe(result => {
      this.dataSource = result.content;
    })
  }

  protected readonly confirm = confirm;
}
