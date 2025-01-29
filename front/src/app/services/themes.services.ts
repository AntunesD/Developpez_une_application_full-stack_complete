import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

import { environment } from '../../environments/environment';
import { Theme } from '../interfaces/theme.interface';

interface SubscribeResponse {
  message: string;
}
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

  // Méthode pour récupérer les thèmes
  getThemes(): Observable<Theme[]> {
    return this.http.get<Theme[]>(this.apiUrl, { headers: this.getHeaders() });
  }

  // Méthode pour s'abonner à un thème
  subscribeToTheme(themeId: number): Observable<SubscribeResponse> {
    const url = `${this.apiUrl}/subscribe`;
    const body = { themeId };

    return this.http.post<SubscribeResponse>(url, body, { headers: this.getHeaders() });
  }
}