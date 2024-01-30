import { Component } from '@angular/core';
import { PagedResult } from 'src/app/model/paged-result.model';
import { ReservationQRCode } from 'src/app/model/reservation-qr-code.model';
import { Reservation } from 'src/app/model/reservation.model';
import { ReservationService } from 'src/app/service/reservation.service';

@Component({
  selector: 'app-user-appointments-qr-codes',
  templateUrl: './user-appointments-qr-codes.component.html',
  styleUrls: ['./user-appointments-qr-codes.component.css']
})
export class UserAppointmentsQrCodesComponent {
  reservations: Reservation[] = [];
  displayedColumns: string[] = ['dateTime', 'duration', 'companyAdmin', 'qrCode'];
  dataSource: Reservation[] = [];
  reservationQrCodes: Map<number, ReservationQRCode> = new Map();
  selectedSortOrder: string = '';

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
    this.reservationService.getReservationsForEmployee().subscribe({
      next: (response: PagedResult<Reservation>) => {
        this.dataSource = response.content;
        this.dataSource.forEach((reservation: Reservation) => {
          this.getQRCode(reservation);
        });
      }
    });
  }

  getQRCode(reservation: Reservation): void {
    this.reservationService.generateQRCodeForReservation(reservation, 200, 200)
      .subscribe((qrCode: ReservationQRCode) => {
        this.reservationQrCodes.set(reservation.id, qrCode);
      });
  }

  getQRCodeImageDataUrl(qrCode: ReservationQRCode): string | undefined {
    if (qrCode.qrCodeImageData) {
      return `data:image/png;base64,${qrCode.qrCodeImageData}`;
    }
    return undefined;
  }
  
  filterReservations(): void {
    if(this.selectedSortOrder === 'Confirmed'){
      this.reservationService.getConfirmedReservationsForUser().subscribe(result => {
        this.dataSource = result;
        this.dataSource.forEach((reservation: Reservation) => {
          this.getQRCode(reservation);
        });
      })
    }

    if(this.selectedSortOrder === 'Cancelled'){
      this.reservationService.getCancelledReservationsForUser().subscribe(result => {
        this.dataSource = result;
        this.dataSource.forEach((reservation: Reservation) => {
          this.getQRCode(reservation);
        });
      })
    }

    if(this.selectedSortOrder === 'Pending'){
      this.reservationService.getPendingReservationsForUser().subscribe(result => {
        this.dataSource = result;
        this.dataSource.forEach((reservation: Reservation) => {
          this.getQRCode(reservation);
        });
      })
    }
    
  }
}


