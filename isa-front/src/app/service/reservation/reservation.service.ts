import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { InventoryItem } from 'src/app/model/inventory.model';
import { PagedResult } from 'src/app/model/paged-result.model';
import { ReservationRequest } from 'src/app/model/reservation/reservation-request.model';
import { Reservation } from 'src/app/model/reservation/reservation.model';
import { environment } from 'src/environments/environment';
import { ReservationItem } from './reservation-item.model';

@Injectable({
  providedIn: 'root'
})
export class ReservationService {

  private url = `${environment.apiUrl}/reservation`;
  private items = `${environment.apiUrl}/reservation-item`;

  constructor(private http: HttpClient) { }

  getAllPaged(): Observable<PagedResult<Reservation>> {
    return this.http.get<PagedResult<Reservation>>(`${this.url}/all`);
  }

  getAvailableAppointments(companyId: number): Observable<Reservation[]> {
    return this.http.get<Reservation[]>(`${this.url}/available/${companyId}`);
  }

  addReservation(reservationRequest: ReservationRequest): Observable<Reservation> {
    return this.http.put<Reservation>(`${this.url}`, reservationRequest);
  }

  getReservationsForEmployee(): Observable<PagedResult<Reservation>> {
    return this.http.get<PagedResult<Reservation>>(`${this.url}/employee`);
  }

  getReservationsForCompanyAdmin(): Observable<PagedResult<Reservation>> {
    return this.http.get<PagedResult<Reservation>>(`${this.url}/company`);
  }

  getByDay(year: number, month: number, day: number): Observable<Reservation[]> {
    return this.http.get<Reservation[]>(`${this.url}/day/${year}/${month}/${day}`);
  }

  getByWeek(year: number, month: number, day: number): Observable<Reservation[]> {
    return this.http.get<Reservation[]>(`${this.url}/week/${year}/${month}/${day}`);
  }

  getByMonth(year: number, month: number): Observable<Reservation[]> {
    return this.http.get<Reservation[]>(`${this.url}/month/${year}/${month}`);
  }

  getByYear(year: number): Observable<Reservation[]> {
    return this.http.get<Reservation[]>(`${this.url}/year/${year}`);
  }

  getReservationItems(): Observable<PagedResult<ReservationItem>> {
    return this.http.get<PagedResult<ReservationItem>>(`${this.items}`);
  }

  cancelReservation(reservationId: number): Observable<Reservation>{
    return this.http.get<Reservation>(`${this.url}/cancel/${reservationId}`);
  }

  startPositionSimulator(reservationId: number): Observable<void> {
    return this.http.get<void>(`${this.url}/tracking/${reservationId}`);
  }

  confirmReservation(reservationId: number): Observable<Reservation>{
    return this.http.get<Reservation>(`${this.url}/confirm/${reservationId}`);
  }

  startDelivery(reservationId: number): Observable<Reservation>{
    return this.http.get<Reservation>(`${this.url}/deliver/${reservationId}`);
  }

  getConfirmedReservations(): Observable<PagedResult<Reservation>>{
    return this.http.get<PagedResult<Reservation>>(`${this.url}/get-confirmed`);
  }
}
