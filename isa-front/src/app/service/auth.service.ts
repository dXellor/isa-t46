import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { HttpClient } from '@angular/common/http'
import { LoginRequest } from '../model/login-request.model';
import { Observable } from 'rxjs';
import { TokenResponse } from '../model/token-response.model';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private url = `${environment.apiUrl}/auth`;

  constructor(private http: HttpClient) {}

  login(loginCreds: LoginRequest): Observable<TokenResponse>{
    return this.http.post<TokenResponse>(`${this.url}/login`, loginCreds)
  } 
}
