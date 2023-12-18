import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { AppointmentRequest } from '../../model/appointment/appointment-request.model';
import { Observable } from 'rxjs';
import { Reservation } from '../../model/reservation/reservation.model';

@Injectable({
  providedIn: 'root'
})
export class AppointmentService {
  private url = `${environment.apiUrl}/reservation`;

  constructor(private http: HttpClient) { }

  addPredefinedAppointment(appointmentRequest: AppointmentRequest): Observable<Reservation> {
    return this.http.post<Reservation>(`${this.url}`, appointmentRequest);
  }

}
