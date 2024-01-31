import {Component, OnInit} from '@angular/core';
import {Reservation} from "../../model/reservation/reservation.model";
import {ReservationService} from "../../service/reservation/reservation.service";
import {ConfirmationService, MessageService} from "primeng/api";
import { ReservationQRCode } from 'src/app/model/reservation-qr-code.model';

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
  private reservationQRCode: ReservationQRCode;

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

   convertToBase64(buffer: any): any {

    console.log(buffer);
    var binary = '';
    var bytes = new Uint8Array(buffer);
    var len = bytes.byteLength;
    for (var i = 0; i < len; i++) {
      binary += String.fromCharCode(bytes[i]);
    }
    return window.btoa(binary);
  }

  onFileSelected(event: any): void {
    const file: File = event.target.files[0];

    if(file) {
      file.arrayBuffer().then(buff => {
        const encoded = this.convertToBase64(buff);
        console.log(encoded);
        this.reservationQRCode = {
          qrCodeImageData: encoded
        }
    });
    }
  }

  confirmReservationbyQR(): void {
    console.log("YES");
    if(this.reservationQRCode) {
      this.reservationService.confirmReservationByQRCode(this.reservationQRCode).subscribe({
        next: (response: Reservation) => {
          console.log("helo");
          this.reservationService.getConfirmedReservations().subscribe(result => {
            this.messageService.add({severity:'success', summary:'Success', detail:'Reservation confirmed.'});
          });
        },
        error: () => {
          this.messageService.add({severity:'error', summary:'Error', detail:`Reservation already confirmed`});
        }
      });
    }
    else {
      this.messageService.add({severity:'error', summary:'Error', detail:`Please select file`});
    }
  }
}
