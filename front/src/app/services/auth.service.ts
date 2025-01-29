import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { LoginRequest } from '../models/login-request.model';
import { AuthResponse } from '../models/auth-response.model';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private apiUrl = `${environment.apiUrl}/auth`;
  private isAuthenticated = false;

  constructor(private http: HttpClient) {
    // Vérifier si un token est présent dans le localStorage lors de l'initialisation du service
    const token = localStorage.getItem('token');
    if (token) {
      this.isAuthenticated = true;
    }
  }

  login(loginRequest: LoginRequest): Observable<AuthResponse> {
    return this.http.post<AuthResponse>(`${this.apiUrl}/login`, loginRequest);
  }

  register(registerRequest: { username: string; email: string; password: string }): Observable<any> {
    return this.http.post<any>(`${this.apiUrl}/register`, registerRequest);
  }

  saveToken(token: string): void {
    localStorage.setItem('token', token);
    this.isAuthenticated = true; // On met à jour l'état d'authentification
  }

  getToken(): string | null {
    return localStorage.getItem('token');
  }

  logout(): void {
    localStorage.removeItem('token');
    this.isAuthenticated = false; // On met à jour l'état d'authentification
  }

  isLoggedIn(): boolean {
    // On vérifie si un token existe et met à jour isAuthenticated si nécessaire
    const token = localStorage.getItem('token');
    this.isAuthenticated = token ? true : false;
    return this.isAuthenticated;
  }
}
