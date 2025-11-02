import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { PropertyInquiry } from '../models/property-inquiry.model';

@Injectable({
  providedIn: 'root'
})
export class PropertyInquiryService {

  private readonly backendUrl="http://localhost:8080/api/inquiries"
  constructor(private readonly http:HttpClient) { }

  getAllInquiries():Observable<PropertyInquiry[]>{
    return this.http.get<PropertyInquiry[]>(this.backendUrl);
  }

  getInquiriesByUserId(userId:number):Observable<PropertyInquiry[]>{
    return this.http.get<PropertyInquiry[]>(`${this.backendUrl}/user/${userId}`);
  }

  addInquiry(inquiry:PropertyInquiry):Observable<PropertyInquiry>{
    return this.http.post<PropertyInquiry>(this.backendUrl,inquiry);
  }

  updateInquiry(inquiryId:number,inquiry:Partial<PropertyInquiry>):Observable<PropertyInquiry>{
    return this.http.put<PropertyInquiry>(`${this.backendUrl}/${inquiryId}`,inquiry);
  }

  deleteInquiry(inquiryId:number):Observable<void>{
    return this.http.delete<void>(`${this.backendUrl}/${inquiryId}`);
  }

  getAdminControlPanelData() : Observable<Map<string, number>> {
    return this.http.get<Map<string, number>>(`${this.backendUrl}/adminControlPanel`)
  }
}
