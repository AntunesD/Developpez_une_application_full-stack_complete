import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

import { environment } from '../../environments/environment';
import { Theme } from '../interfaces/theme.interface';
import { Profil } from '../interfaces/profil.interface';
import { AuthResponse } from '../models/auth-response.model';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private apiUrl = `${environment.apiUrl}/user`;

  constructor(private http: HttpClient) { }

  private getHeaders(): HttpHeaders {
    const token = localStorage.getItem('token');
    return new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${token}`
    });
  }

  getUser(): Observable<Profil> {
    return this.http.get<Profil>(this.apiUrl, { headers: this.getHeaders() });
  }

  updateUser(username: string, email: string): Observable<AuthResponse> {
    const payload = {
      username,
      email
    };

    return this.http.patch(this.apiUrl, payload, { headers: this.getHeaders() }) as Observable<AuthResponse>;
  }
}