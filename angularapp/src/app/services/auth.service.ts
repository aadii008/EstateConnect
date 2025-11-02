import { Injectable } from '@angular/core';
import { User } from '../models/user.model';
import { HttpClient } from '@angular/common/http';
import { BehaviorSubject, Observable} from 'rxjs';
import { Login } from '../models/login.model';
import { map } from 'rxjs/operators'

export const AUTHENTICATED_USER = 'authenticatedUser';
export const TOKEN = 'token';
export const PAGE_ID = 'pageId';
export const USER_ID = 'userId';
export const ROLE = 'role';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private readonly backendUrl : string = "http://localhost:8080"

  private readonly userRoleSubject = new BehaviorSubject<string | null>(null);
  userRole$: Observable<string | null> = this.userRoleSubject.asObservable();

  constructor(private readonly http : HttpClient) {
    const storedRole = localStorage.getItem('role');
    if (storedRole) {
      this.userRoleSubject.next(storedRole);
    }
   }

  register(user : User) : Observable<User> {
    return this.http.post<User>(`${this.backendUrl}/api/register`, user);
  }

  login(login : Login): Observable<Map<string, Object>> {
    return this.http.post<Map<string, Object>>(`${this.backendUrl}/api/login`, login).pipe(
      map(
        data => {
          localStorage.setItem(USER_ID, "" + data['userId']);
          localStorage.setItem(AUTHENTICATED_USER, data['username']);
          localStorage.setItem(TOKEN, data['jwtToken'])
          localStorage.setItem(ROLE, data['role']);
          this.userRoleSubject.next(data['role']);
          return data;
        }
      )
    );
  }

  
  getUserById(userId: number): Observable<User> {
    return this.http.get<User>(`${this.backendUrl}/api/user/${userId}`);
  }

  
  getUsernameById(userId: number): Observable<string> {
    return this.getUserById(userId).pipe(
      map(user => user.username)
    );
  }

  clearLocalStorage(): void {
    // Clear the whole localStorage
    localStorage.clear();

    // Emit null to the BehaviorSubject to notify subscribers that the user is logged out
    this.userRoleSubject.next(null);
  }


  getRole(): string {
    return localStorage.getItem('role');
  }

  isLoggedIn(): boolean {
    let user = localStorage.getItem(AUTHENTICATED_USER);
    return (user != null);
  }
}
