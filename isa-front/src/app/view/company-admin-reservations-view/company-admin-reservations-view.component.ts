import {Component, OnInit} from '@angular/core';
import {Reservation} from "../../model/reservation/reservation.model";
import {ReservationService} from "../../service/reservation/reservation.service";
import {ConfirmationService, MessageService} from "primeng/api";

@Component({
  selector: 'app-company-admin-reservations-view',
  templateUrl: './company-admin-reservations-view.component.html',
  styleUrls: ['./company-admin-reservations-view.component.css'],
  providers: [MessageService, ConfirmationService]
})
export class CompanyAdminReservationsViewComponent implements OnInit{
  reservations: Reservation[] = [];
  displayedColumns: string[] = ['dateTime', 'duration', 'companyAdmin', 'start'];
  dataSource: Reservation[] = [];

  constructor(private reservationService: ReservationService, private messageService: MessageService, private confirmationService: ConfirmationService) { }
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
      this.messageService.add({severity:'success', summary:'Success', detail:'Delivery completed.'});
    },
      error => {
        this.messageService.add({severity:'error', summary:'Error', detail:`You can't start another delivery before other one finishes.`});
      }
    );
  }

  formatDate(dateT: Date): string {
    const dateTime = new Date(dateT);
    const date = `${dateTime.getDate()}.${dateTime.getMonth() + 1}.${dateTime.getFullYear()}`;
    const time = `${dateTime.getHours()}:${String(dateTime.getMinutes()).padStart(2, '0')}`;
    return `${date} - ${time}`
  }

  isToday(date: Date):boolean {
    const today = new Date();
    const dateString = new Date(date);
    return dateString.getDate() === today.getDate() &&
      dateString.getMonth() === today.getMonth() &&
      dateString.getFullYear() === today.getFullYear();
  }

}
