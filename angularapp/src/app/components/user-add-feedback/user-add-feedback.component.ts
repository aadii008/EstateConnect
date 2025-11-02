import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Feedback } from 'src/app/models/feedback.model';
import { FeedbackService } from 'src/app/services/feedback.service';
import { PropertyService } from 'src/app/services/property.service';
 
@Component({
  selector: 'app-user-add-feedback',
  templateUrl: './user-add-feedback.component.html',
  styleUrls: ['./user-add-feedback.component.css']
})
export class UserAddFeedbackComponent implements OnInit {
  feedbackText: string = '';
  date: string = '';
  category: string = '';
  selectedPropertyId: number | null = null;
  allProperties: { propertyId: number; name: string; type: string }[] = [];
  properties: { propertyId: number; name: string; type: string }[] = [];
  userId: number = 0;
  isSucessFull : boolean = false;
 
  showSuccessPopup: boolean = false;
  showErrorPopup: boolean = false;
  isMessageFilled: boolean = false;
 
  constructor(
    private readonly router: Router,
    private readonly propertyService: PropertyService,
    private readonly feedbackService: FeedbackService
  ) {}
 
  ngOnInit(): void {
    this.date = new Date().toISOString().split('T')[0];
    
    const storedUserId = localStorage.getItem('userId');
    if (storedUserId) {
      this.userId = +storedUserId;
    }

    this.loadProperties();
  }
 
  loadProperties(): void {
    this.propertyService.getAllProperties().subscribe({
      next: (data: any[]) => {
        this.allProperties = data;
        this.properties = data;
        this.filterPropertiesByCategory();
      },
      error: (err) => {
        console.error('Error loading properties:', err);
      }
    });
  }
 
  filterPropertiesByCategory(): void {
    if (this.category) {
      this.properties = this.allProperties.filter(
        (p) => p.type === this.category
      );
    } else {
      this.properties = [];
    }
  }
 
  onMessageInput(): void {
    this.isMessageFilled = this.feedbackText.trim().length > 0;
  }
 
  onSubmit(): void {
    if (this.feedbackText && this.date && this.category && this.userId) {
      const feedbackData: Feedback = {
        feedbackText: this.feedbackText,
        date: this.date,
        category: this.category,
        user: {
          userId: this.userId
        },
        property: this.selectedPropertyId
          ? { propertyId: this.selectedPropertyId }
          : undefined
      };
 
      this.feedbackService.submitFeedback(feedbackData).subscribe({
        next: () => {
          this.showSuccessPopup = true;
          this.showErrorPopup = false;
          this.isSucessFull = true;
        },
        error: (err) => {
          console.error('Error submitting feedback:', err);
          this.showErrorPopup = true;
          this.showSuccessPopup = false;
          this.isSucessFull = false;
        }
      });
    } else {
      this.showErrorPopup = true;
      this.showSuccessPopup = false;
    }
  }
 
  closePopup(): void {
    this.showSuccessPopup = false;
    this.showErrorPopup = false;
    if(this.isSucessFull){
      this.router.navigate(['/view-feedback']);
    }
  }
}