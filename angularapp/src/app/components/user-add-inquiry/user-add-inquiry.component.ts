import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Property } from 'src/app/models/property.model';
import { PropertyInquiryService } from 'src/app/services/property-inquiry.service';
import { PropertyService } from 'src/app/services/property.service';
import { PropertyInquiry } from "../../models/property-inquiry.model";
 
@Component({
  selector: 'app-user-add-inquiry',
  templateUrl: './user-add-inquiry.component.html',
  styleUrls: ['./user-add-inquiry.component.css']
})
export class UserAddInquiryComponent implements OnInit {
 
  savedProperty : Property;
  propertyId: number | null = null;
  inquiryForm: FormGroup;
  showPopUp : boolean = false;
  userId : number | null = +localStorage.getItem("userId");
  popUpMessage : string = "";
 
  constructor(
    private readonly fb: FormBuilder,
    private readonly route: ActivatedRoute,
    private readonly router: Router,
    private readonly propertyService : PropertyService,
    private readonly propertyInquiryService : PropertyInquiryService
  ) {
    this.inquiryForm = this.fb.group({
      property: ['', Validators.required],
      message: ['', Validators.required],
      priority: ['', Validators.required]
    });
  }
 
  ngOnInit(): void {
 
    this.propertyId = +this.route.snapshot.paramMap.get('propertyId');
 
    if (this.propertyId) {
 
      this.propertyService.getPropertyById(this.propertyId).subscribe(
        (data) => {
 
          this.savedProperty = data;
 
          this.inquiryForm.patchValue({
            property: this.savedProperty?.title
          });
 
        }
      )
    }
  }
 
  onSubmit() {
   
    if (this.inquiryForm.valid) {
 
      const inquiryRequestBody : PropertyInquiry = {
        message : this.inquiryForm.value.message,
        priority : this.inquiryForm.value.priority,
        user : {
          userId : +localStorage.getItem('userId')
        },
        property : {
          propertyId : this.propertyId
        },
        status: "Pending",
        inquiryDate : new Date().toISOString().split('T')[0].toString()
      }
   
      this.propertyInquiryService.addInquiry(inquiryRequestBody).subscribe({
        next: () => {
          this.showPopUp = true;
          this.popUpMessage = "Inquiry submitted successfully";
        },
        error: (err) => {
          console.error('Error submitting feedback:', err);
          this.showPopUp = true;
     
          if (err.error && typeof err.error === 'string') {
            this.popUpMessage = err.error;
          } else {
            this.popUpMessage = 'An unexpected error occurred. Please try again.';
          }
        }
      });
    } else {
      console.log('Form is invalid');
    }
  }
 
  onOkClick(){
    this.router.navigate(["propertyInquiries", this.userId])
  }
 
 
}
 
 