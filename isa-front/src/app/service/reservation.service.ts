import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { Reservation } from '../model/reservation.model';
import { Observable } from 'rxjs';
import { PagedResult } from '../model/paged-result.model';
import { ReservationItem } from '../model/reservation/reservation-item.model';
import { OutOfOrderReservation } from '../model/out-of-order-reservation-model';

@Injectable({
  providedIn: 'root'
})
export class ReservationService {
  
  private url = `${environment.apiUrl}/reservation`;
  constructor(private http: HttpClient) {}

  getByDay(year: number, month: number, day: number): Observable<Reservation[]>{
    return this.http.get<Reservation[]>(`${this.url}/day/${year}/${month}/${day}`);
  }

  getByWeek(year: number, month: number, day: number): Observable<Reservation[]>{
    return this.http.get<Reservation[]>(`${this.url}/week/${year}/${month}/${day}`);
  }

  getByMonth(year: number, month: number): Observable<Reservation[]>{
    return this.http.get<Reservation[]>(`${this.url}/month/${year}/${month}`);
  }

  getByYear(year: number): Observable<Reservation[]>{
    return this.http.get<Reservation[]>(`${this.url}/year/${year}`);
  }

  getAvailableTimeSlots(companyId: number, date: Date): Observable<Reservation[]> {
    const formattedDate = new Date(date).toISOString().split('T')[0];
    const params = {
      companyId: companyId.toString(),
      date: formattedDate,
    };
    
    return this.http.get<Reservation[]>(`${this.url}/availableTimeSlots`, { params });
  }

  createReservationWithOutOfOrderAppointment(outOfOrderReservation: OutOfOrderReservation): Observable<Reservation> {
    return this.http.put<Reservation>(`${this.url}/outOfOrderCreate`, outOfOrderReservation);
  }
}

