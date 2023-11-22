import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { HttpClient } from '@angular/common/http'
import { LoginRequest } from '../model/auth/login-request.model';
import { Observable } from 'rxjs';
import { TokenResponse } from '../model/auth/token-response.model';
import { RegisterRequest } from '../model/auth/register-request.model';
import { User } from '../model/user.model';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private url = `${environment.apiUrl}/auth`;

  constructor(private http: HttpClient) {}

  login(loginCreds: LoginRequest): Observable<TokenResponse>{
    return this.http.post<TokenResponse>(`${this.url}/login`, loginCreds)
  }
  
  register(registerInfo: RegisterRequest): Observable<User>{
    return this.http.post<User>(`${this.url}/register`, registerInfo);
  }

  registerCompanyAdmin(adminRegisterInfo: User): Observable<User>{
    return this.http.post<User>(`${this.url}/register/ca`, adminRegisterInfo);
  }

  verify(token: string): Observable<User>{
    return this.http.get<User>(`${this.url}/verify/${token}`);
  }
}
