import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { Reservation } from '../model/reservation.model';
import { Observable } from 'rxjs';

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
}
