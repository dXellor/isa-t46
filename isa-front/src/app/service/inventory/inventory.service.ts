import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { NumberValueAccessor } from '@angular/forms';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { PagedResult } from '../../model/paged-result.model';
import { InventoryItem } from '../../model/inventory.model';

@Injectable({
  providedIn: 'root'
})
export class InventoryService {
  private url = `${environment.apiUrl}/inventory`;

  constructor(private http: HttpClient) { }

  getInventoryForCompany(companyId: number): Observable<PagedResult<InventoryItem>> {
    return this.http.get<PagedResult<InventoryItem>>(`${this.url}/company/` + companyId);
  }

}
