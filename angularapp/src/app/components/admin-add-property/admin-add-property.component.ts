import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { PropertyService } from 'src/app/services/property.service';

@Component({
  selector: 'app-admin-add-property',
  templateUrl: './admin-add-property.component.html',
  styleUrls: ['./admin-add-property.component.css']
})
export class AdminAddPropertyComponent implements OnInit {

  propertyForm: FormGroup;
  propertyTypes: string[] = ['Residential', 'Commercial', 'Land'];
  propertyStatuses: string[] = ['Available', 'Under Contract', 'Sold'];
  errorMessage: string = '';

  constructor(
    private readonly fb: FormBuilder,
    private readonly propertyService : PropertyService,
    private readonly router : Router
  ) { }

  ngOnInit(): void {

    this.propertyForm = this.fb.group({
      title: ['', Validators.required],
      description: ['', Validators.required],
      location: ['', Validators.required],
      price: ['', [Validators.required, Validators.min(0)]],
      type: [this.propertyTypes[0], Validators.required],
      status: [this.propertyStatuses[0], Validators.required]
    });

  }


  onSubmit(): void {
    if (this.propertyForm.valid) {
      this.propertyService.addProperty(this.propertyForm.value).subscribe(
        (data) => {
          this.handleSuccess();
        },
        (error) => {
          this.handleError(error);
        }
      )
    }
  }

  onClose(): void {
    this.handleSuccess();
  }

  private handleSuccess(): void {
    this.errorMessage = '';
    this.router.navigate(['/admin-view-properties']);
  }

  private handleError(error: any): void {
    if (error.status === 409) {
      this.errorMessage = error.error.message ?? 'Property with this title already exists.';
    } else {
      this.errorMessage = 'An unexpected error occurred. Please try again.';
    }
  }
}