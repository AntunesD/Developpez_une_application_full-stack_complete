import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

import { environment } from '../../environments/environment';
import { Themes } from '../interfaces/themes.interface';

@Injectable({
  providedIn: 'root'
})
export class ThemesService {
  private apiUrl = `${environment.apiUrl}/themes`;

  constructor(private http: HttpClient) { }

  private getHeaders(): HttpHeaders {
    const token = localStorage.getItem('token');
    return new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${token}`
    });
  }

  getThemes(): Observable<Themes[]> {
    return this.http.get<Themes[]>(this.apiUrl, { headers: this.getHeaders() });
  }
}