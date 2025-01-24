import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { CommentResponse } from '../models/comment-response.model';

@Injectable({
  providedIn: 'root'
})
export class CommentsService {
  private apiUrl = 'http://localhost:8080/api/comments';

  constructor(private http: HttpClient) { }

  private getHeaders(): HttpHeaders {
    const token = localStorage.getItem('token');
    return new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${token}`
    });
  }

  postComment(articleId: number, content: string): Observable<CommentResponse> {
    const payload = {
      articleId,
      content
    };

    return this.http.post(this.apiUrl, payload, { headers: this.getHeaders() }) as Observable<CommentResponse>;
  }
}
