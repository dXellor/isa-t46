import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { PagedResult } from 'src/app/model/paged-result.model';
import { ReservationRequest } from 'src/app/model/reservation/reservation-request.model';
import { Reservation } from 'src/app/model/reservation/reservation.model';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class ReservationService {

  private url = `${environment.apiUrl}/reservation`;

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

}
