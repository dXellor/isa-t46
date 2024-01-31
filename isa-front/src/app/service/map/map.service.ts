import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class MapService {

  private readonly geoCodingApi = 'https://nominatim.openstreetmap.org/search';

  constructor(private http: HttpClient) {}

  search(street: string): Observable<any> {
    const params = new HttpParams().set('format', 'json').set('q', street);

    return this.http.get(this.geoCodingApi, { params }).pipe(
      catchError(this.handleError)
    );
  }
  private handleError(error: any): Observable<never> {
    console.error('An error occurred', error);
    return throwError(error);
  }
}
