import { Injectable } from '@angular/core';
import { Equipment } from '../model/equipment.model';
import { Observable } from 'rxjs';
import { PagedResult } from '../model/paged-result.model';
import { environment } from 'src/environments/environment';
import { HttpClient, HttpParams } from '@angular/common/http';
import { EquipmentFilterCriteria } from '../model/equipment-filter-criteria.model';

@Injectable({
  providedIn: 'root'
})
export class EquipmentService {

  private url = `${environment.apiUrl}/equipment`;
  private temporari = `http://localhost:8080/equipment`; // without api in string, don't want to mess up someones work, so didn't change it in controller but it's without 'api' in controller / -zanya

  constructor(private http: HttpClient) {}

  getAll(): Observable<PagedResult<Equipment>>{
    return this.http.get<PagedResult<Equipment>>(`${this.temporari}/all`)
  }

  getFiltered(filter: EquipmentFilterCriteria): Observable<PagedResult<Equipment>>{
    const params = new HttpParams()
      .set('name', filter.name ? filter.name?.toString() : "")
      .set('priceMin', filter.priceMin ? filter.priceMin?.toString() : "")
      .set('priceMax', filter.priceMax ? filter.priceMax?.toString() : "")
      .set('type', filter.type ? filter.type?.toString() : "")
    return this.http.get<PagedResult<Equipment>>(`${this.temporari}`, { params })
  }
}
