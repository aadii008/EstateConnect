import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Property } from '../models/property.model';

@Injectable({
  providedIn: 'root'
})
export class PropertyService {
  private readonly backendUrl="http://localhost:8080/api/properties"
  constructor(private readonly http:HttpClient) { }

  getAllProperties():Observable<Property[]>{
    return this.http.get<Property[]>(this.backendUrl);
  }

  getPropertyById(propertyId:number):Observable<Property>{
    return this.http.get<Property>(`${this.backendUrl}/${propertyId}`);
  }

  addProperty(property:Property):Observable<Property>{
    return this.http.post<Property>(this.backendUrl,property);
  }

  updateProperty(propertyId:number,property:Property):Observable<Property>{
    return this.http.put<Property>(`${this.backendUrl}/${propertyId}`,property);
  }

  deleteProperty(propertyId:number):Observable<void>{
    return this.http.delete<void>(`${this.backendUrl}/${propertyId}`);
  }
  
}
