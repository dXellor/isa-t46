import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { PagedResult } from 'src/app/model/paged-result.model';
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

}
