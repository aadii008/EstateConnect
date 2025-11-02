import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Property } from 'src/app/models/property.model';
import { PropertyService } from 'src/app/services/property.service';

@Component({
  selector: 'app-user-view-properties',
  templateUrl: './user-view-properties.component.html',
  styleUrls: ['./user-view-properties.component.css']
})
export class UserViewPropertiesComponent implements OnInit {
  properties:Property[]=[];
  errorMessage='';

  constructor(private readonly propertyService:PropertyService, private readonly router:Router) { }

  ngOnInit(): void {
    this.loadProperties();
  }

  loadProperties(){
    this.propertyService.getAllProperties().subscribe({
      next:(data)=>(this.properties=data),
      error:()=>(this.errorMessage='Failed to load Properties.')
    });
  }

  navigateToInquiry(propertyId?: number): void {
    if (propertyId) {
      this.router.navigate(['/user-add-inquiry', propertyId]);
    }
  }

}
