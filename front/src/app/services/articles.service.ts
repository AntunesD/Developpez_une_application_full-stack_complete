import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Article } from '../interfaces/article.interface';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class ArticlesService {
  private apiUrl = `${environment.apiUrl}/articles`;

  constructor(private http: HttpClient) { }

  private getHeaders(): HttpHeaders {
    const token = localStorage.getItem('token');
    return new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${token}`
    });
  }

  getArticles(): Observable<Article[]> {
    return this.http.get<Article[]>(this.apiUrl, { headers: this.getHeaders() });
  }
  // Nouvelle méthode pour obtenir un article par ID
  getArticleById(id: number): Observable<Article> {
    const url = `${this.apiUrl}/${id}`;
    return this.http.get<Article>(url, { headers: this.getHeaders() });
  }

  // Nouvelle méthode pour créer un article
  createArticle(article: { title: string, content: string, theme: { id: string } }): Observable<Article> {
    return this.http.post<Article>(this.apiUrl, article, { headers: this.getHeaders() });
  }
}