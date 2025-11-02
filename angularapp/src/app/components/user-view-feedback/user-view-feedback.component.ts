import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Feedback } from 'src/app/models/feedback.model'; 
import { FeedbackService } from 'src/app/services/feedback.service';
import { PropertyService } from 'src/app/services/property.service';


@Component({
  selector: 'app-user-view-feedback',
  templateUrl: './user-view-feedback.component.html',
  styleUrls: ['./user-view-feedback.component.css']
})
export class UserViewFeedbackComponent implements OnInit {

  feedbackList: Feedback[] = [];


  feedbackPropertyString: string;
  constructor(
    private readonly feedbackService: FeedbackService,
    private readonly propertyService: PropertyService,
    private readonly router : Router
  ) {}

  ngOnInit(): void {
    this.loadUserFeedback();
  }

  loadUserFeedback(): void {
    const userId = +localStorage.getItem('userId');

    this.feedbackService.getFeedbackByUserId(userId).subscribe((feedbacks: Feedback[]) => {
      this.feedbackList = feedbacks;

      // Fetch property names for each feedback
      this.feedbackList.forEach(feedback => {
        if (feedback.property?.propertyId) {
          this.propertyService.getPropertyById(feedback.property.propertyId).subscribe(property => {
            this.feedbackPropertyString = property.title;
          });
        }
      });
    });
  }

  handleBottomRightClick(){
    this.router.navigate(['/add-feedback']); 
  }
}
