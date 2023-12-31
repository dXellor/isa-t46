import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { Company } from '../model/company.model';
import { Observable } from 'rxjs';
import { PagedResult } from '../model/paged-result.model';
import { Equipment } from '../model/equipment.model';

@Injectable({
  providedIn: 'root'
})
export class CompanyService {

  private url = `${environment.apiUrl}/companies`;

  constructor(private http: HttpClient) { }

  getAll(): Observable<PagedResult<Company>>{
    return this.http.get<PagedResult<Company>>(`${this.url}/all`);
  }

  addCompany(company: Company): Observable<Company>{
    return this.http.post<Company>(`${this.url}`, company);
  }

  updateCompany(company: Company): Observable<Company>{
    return this.http.put<Company>(`${this.url}`, company);
  }

  getByEquipment(equipmentId: number): Observable<PagedResult<Company>>{
    return this.http.get<PagedResult<Company>>(`${this.url}/${equipmentId}`);
  }

  searchCompanies(name?: string, city?: string, country?: string): Observable<PagedResult<Company>> {
    const params = { name, city, country };
    return this.http.get<PagedResult<Company>>(`${this.url}/searchByNameCityCountry`, { params });
  }

  filterCompanies(avgRatingMin?: number, avgRatingMax?: number): Observable<PagedResult<Company>>{
    const params = {avgRatingMin, avgRatingMax};
    return this.http.get<PagedResult<Company>>(`${this.url}/filterByRating`, { params });
  }
  
  getCompanyByAdminId(adminId: number): Observable<Company> {
    return this.http.get<Company>(`${this.url}/admin/${adminId}`);
  }
}
