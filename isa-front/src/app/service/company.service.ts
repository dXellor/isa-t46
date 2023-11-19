import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { Company } from '../model/company-model';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class CompanyService {

  private url = `${environment.apiUrl}`;

  constructor(private http: HttpClient) {
    
  }
  searchCompanies(name?: string, city?: string, country?: string): Observable<Company[]> {
    const params = { name, city, country };
    return this.http.get<Company[]>(`${this.url}/companies/searchByNameCityCountry`, { params });
  }

  findAll(): Observable<Company[]> {
    return this.http.get<Company[]>(`${this.url}/companies/all`);
  }
  
}