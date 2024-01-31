import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { NumberValueAccessor } from '@angular/forms';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { PagedResult } from '../../model/paged-result.model';
import { InventoryItem } from '../../model/inventory.model';
import { InvokeFunctionExpr } from '@angular/compiler';

@Injectable({
  providedIn: 'root'
})
export class InventoryService {
  private url = `${environment.apiUrl}/inventory`;

  constructor(private http: HttpClient) { }

  getInventoryForCompany(companyId: number): Observable<PagedResult<InventoryItem>> {
    return this.http.get<PagedResult<InventoryItem>>(`${this.url}/company/` + companyId);
  }

  addInventory(inventoryItem: InventoryItem): Observable<InventoryItem> {
    return this.http.post<InventoryItem>(`${this.url}`, inventoryItem);
  }

  removeInventoryItem(id: number): Observable<void> {
    return this.http.delete<void>(`${this.url}/${id}`);
  }

  updateInventoryItem(inventoryItem: InventoryItem): Observable<InventoryItem> {
    return this.http.put<InventoryItem>(`${this.url}`, inventoryItem);
  }
}
