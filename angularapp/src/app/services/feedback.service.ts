import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Feedback } from '../models/feedback.model';

@Injectable({
  providedIn: 'root'
})
export class FeedbackService {

  private readonly baseUrl = 'http://localhost:8080/api/feedback';

  constructor(private readonly http: HttpClient) {}

  getAllFeedback(): Observable<Feedback[]> {
    return this.http.get<Feedback[]>(`${this.baseUrl}`);
  }

  getFeedbackById(feedbackId: number): Observable<Feedback> {
    return this.http.get<Feedback>(`${this.baseUrl}/${feedbackId}`);
  }

  getFeedbackByUserId(userId: number): Observable<Feedback[]> {
    return this.http.get<Feedback[]>(`${this.baseUrl}/user/${userId}`);
  }

  submitFeedback(feedback: Feedback): Observable<Feedback> {
    return this.http.post<Feedback>(`${this.baseUrl}`, feedback);
  }

  updateFeedback(feedbackId: number, updatedFeedback: Feedback): Observable<Feedback> {
    return this.http.put<Feedback>(`${this.baseUrl}/${feedbackId}`, updatedFeedback);
  }

  deleteFeedback(feedbackId: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${feedbackId}`);
  }
}
