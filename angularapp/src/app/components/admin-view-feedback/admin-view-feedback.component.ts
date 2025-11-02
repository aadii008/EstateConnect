import { Component, OnInit } from '@angular/core';
import { Feedback } from 'src/app/models/feedback.model';
import { Property } from 'src/app/models/property.model';
import { User } from 'src/app/models/user.model';
import { AuthService } from 'src/app/services/auth.service';
import { FeedbackService } from 'src/app/services/feedback.service';
import { PropertyService } from 'src/app/services/property.service';


@Component({
  selector: 'app-admin-view-feedback',
  templateUrl: './admin-view-feedback.component.html',
  styleUrls: ['./admin-view-feedback.component.css']
})
export class AdminViewFeedbackComponent implements OnInit {
  feedbacks: Feedback[] = [];
  filteredFeedbacks: Feedback[] = [];
  categories: string[] = [];
  selectedCategory: string = 'All';
  selectedProperty:Property | null;
  selectedUser:User | null;
  usernamesMap: Map<number, string> = new Map();

  constructor(
    private readonly feedbackService: FeedbackService,
    private readonly authService: AuthService,
    private readonly propertyService: PropertyService
  ) {}

  ngOnInit(): void {
    this.feedbackService.getAllFeedback().subscribe(data => {
      this.feedbacks = data;
      this.filteredFeedbacks = data;
      this.categories = [...new Set(data.map(f => f.category))];

      // Fetch usernames for each unique userId
      const uniqueUserIds = [...new Set(data.map(f => f.user.userId))];
      uniqueUserIds.forEach(userId => {
        this.authService.getUsernameById(userId).subscribe(username => {
          this.usernamesMap.set(userId, username);
        });
      });
    });
  }

  filterFeedbacks(): void {
    if (this.selectedCategory === 'All') {
      this.filteredFeedbacks = this.feedbacks;
    } else {
      this.filteredFeedbacks = this.feedbacks.filter(f => f.category === this.selectedCategory);
    }
  }

  
  getUserName(userId: number): string {
    return this.usernamesMap.get(userId) || 'Loading...';
  }


  viewUserDetails(userId: number): void {
    this.authService.getUserById(userId).subscribe((data) =>{
      this.selectedUser = data;
    });
  }

  viewPropertyDetails(propertyId: number): void {
    this.propertyService.getPropertyById(propertyId).subscribe((data) =>{
      this.selectedProperty = data;
    })
  }

  
  closeModal(): void {
    this.selectedUser = null;
    this.selectedProperty = null;
  }

}
