import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Property } from 'src/app/models/property.model';
import { PropertyService } from 'src/app/services/property.service';

@Component({
  selector: 'app-admin-edit-property',
  templateUrl: './admin-edit-property.component.html',
  styleUrls: ['./admin-edit-property.component.css']
})
export class AdminEditPropertyComponent implements OnInit {

  propertyForm: FormGroup;
  propertyId: number | null = null;
  savedProperty : Property;
  submitted = false;
  propertyTypes: string[] = ['Residential', 'Commercial', 'Land'];
  propertyStatuses: string[] = ['Available', 'Under Contract', 'Sold'];


  constructor(
    private readonly fb: FormBuilder,
    private readonly route: ActivatedRoute,
    private readonly router: Router,
    private readonly propertyService : PropertyService
  ) {
    
  }

  ngOnInit(): void {
    this.propertyForm = this.fb.group({
      title: ['', Validators.required],
      description: ['', Validators.required],
      location: ['', Validators.required],
      price: [0, [Validators.required, Validators.min(0)]],
      type: ['', Validators.required],
      status: ['', Validators.required],
    });
  
    this.propertyId = +this.route.snapshot.paramMap.get('propertyId');
  
    if (this.propertyId) {
      this.propertyService.getPropertyById(this.propertyId).subscribe(
        (data) => {
          this.savedProperty = data;
          
          this.propertyForm.patchValue(this.savedProperty);
        }
      );
    }
  }
  

  onSubmit(): void {
    this.submitted = true;
    if (this.propertyForm.invalid) {
      return;
    }
  
    this.propertyService.updateProperty(this.propertyId, this.propertyForm.value).subscribe(
      (response) => {
        console.log('Property updated successfully', response);
        this.router.navigate(['/admin-view-properties']);
      },
      (error) => {
        console.error('Error updating property', error);
      
      }
    );
  }
  

  onCancel(): void {
    this.router.navigate(['/admin-view-properties']);
  }
}
