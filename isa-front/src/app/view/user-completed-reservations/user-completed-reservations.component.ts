import { ChangeDetectorRef, Component } from '@angular/core';
import { forkJoin } from 'rxjs';
import { Reservation } from 'src/app/model/reservation.model';
import { ReservationService } from 'src/app/service/reservation.service';

@Component({
  selector: 'app-user-completed-reservations',
  templateUrl: './user-completed-reservations.component.html',
  styleUrls: ['./user-completed-reservations.component.css']
})
export class UserCompletedReservationsComponent {
  reservations: Reservation[] = [];
  displayedColumns: string[] = ['dateTime', 'duration', 'companyAdmin', 'totalPrice'];
  dataSource: Reservation[] = [];
  sortByDuration: boolean = false;
  sortByDateTime: boolean = false;
  sortByPrice: boolean = false;
  selectedSortOrder: string = 'asc';
  pricesMap: Map<number, number> = new Map<number, number>();

  constructor(private reservationService: ReservationService, private cdr: ChangeDetectorRef) { }
  
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
      this.calculateTotalPriceForReservations(result);
    })
  }

  sortReservations(): void {
    this.reservationService.sortReservationsByDurationAndDate(
      this.sortByDuration,
      this.sortByDateTime,
      this.selectedSortOrder
    ).subscribe(sortedReservations => this.calculateTotalPriceForReservations(sortedReservations));
  }

  calculateTotalPriceForReservations(reservations: Reservation[]): void {
    const observables = reservations.map(reservation =>
      this.reservationService.getReservationItemsByReservationId(reservation.id!)
    );
  
    forkJoin(observables).subscribe((reservationItemsArray: any[]) => {
      this.dataSource = reservations.map((reservation, index) => {
        const totalPrice = reservationItemsArray[index].reduce((sum: number, item: any) => {
          const itemPrice = item.inventoryItem?.equipment?.price || 0;
          const itemCount = item.count || 0; 
  
          return sum + (isNaN(itemPrice) || isNaN(itemCount) ? 0 : itemPrice * itemCount);
        }, 0);
  
        this.pricesMap.set(reservation.id!, totalPrice);
  
        return { ...reservation, totalPrice };
      });
      if(this.sortByPrice){
        this.sortReservationsByPrice();
      }
    });

  }
  
  private sortReservationsByPrice(): void {
    console.log('Sorting by price');
    
    const sortedData = [...this.dataSource].sort((a, b) => {
      const priceA = this.pricesMap.get(a.id!) || 0;
      const priceB = this.pricesMap.get(b.id!) || 0;

      //console.log('priceA:', priceA);
      //console.log('priceB:', priceB);
  
      if (this.selectedSortOrder === 'asc') {
        return priceA - priceB;
      } else {
        return priceB - priceA;
      }
    });
    //console.log('Sorted data:', sortedData);
    this.dataSource = sortedData;
    this.cdr.detectChanges();
  }
}