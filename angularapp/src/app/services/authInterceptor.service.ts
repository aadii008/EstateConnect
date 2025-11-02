import { HttpClient, HttpEvent, HttpHandler, HttpInterceptor, HttpRequest } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthInterceptor implements HttpInterceptor {

  constructor(private readonly http : HttpClient) {}

  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    
    const token = localStorage.getItem('token');
    if (token) {
      const clonedRequest = request.clone({
        setHeaders: {
          'Authorization': `Bearer ${token}`,
        
        }, withCredentials: true
      });

      return next.handle(clonedRequest);
    }
    
    return next.handle(request);
  }

}
