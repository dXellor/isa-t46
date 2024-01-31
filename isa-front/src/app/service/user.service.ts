import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { PagedResult } from '../model/paged-result.model';
import { User } from '../model/user.model';
import { BehaviorSubject, Observable } from 'rxjs';
import { EditUserRequest } from '../model/edit-user-request.model';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private loggedInUserSubject: BehaviorSubject<User>;
  public loggedInUserTrigger: Observable<User>;

  private url = `${environment.apiUrl}/user`;

  constructor(private http: HttpClient) {
    this.loggedInUserSubject = new BehaviorSubject<User>(null);
    this.loggedInUserTrigger = this.loggedInUserSubject.asObservable();
  }


  //API
  getAll(): Observable<PagedResult<User>> {
    return this.http.get<PagedResult<User>>(`${this.url}/all`);
  }

  getAllCompanyAdmins(): Observable<PagedResult<User>> {
    return this.http.get<PagedResult<User>>(`${this.url}/all/ca`);
  }

  getUser(): Observable<User> {
    return this.http.get<User>(this.url);
  }

  updateUser(editUserInfo: EditUserRequest): Observable<User> {
    return this.http.post<User>(`${this.url}`, editUserInfo);
  }

  //OTHER
  setLoggedInUser(): void {
    this.getUser().subscribe({
      next: (user) => {
        this.loggedInUserSubject.next(user);
      },

      error: (err) => {
        window.localStorage.removeItem('jwt');
        this.loggedInUserSubject.next(null);
      },
    });
  }

  getCurrentUser(): User {
    return this.loggedInUserSubject.getValue();
  }

  passwordChanged(): void {
    this.loggedInUserSubject.getValue().pendingPasswordReset = false;
  }
}
