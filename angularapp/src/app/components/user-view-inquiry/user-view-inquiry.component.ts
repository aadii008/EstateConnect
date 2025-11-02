import { Component, OnInit } from '@angular/core';
import { PropertyInquiry } from 'src/app/models/property-inquiry.model';
import { PropertyInquiryService } from 'src/app/services/property-inquiry.service';

@Component({
  selector: 'app-user-view-inquiry',
  templateUrl: './user-view-inquiry.component.html',
  styleUrls: ['./user-view-inquiry.component.css']
})
export class UserViewInquiryComponent implements OnInit {

  inquiries: PropertyInquiry[] = [];
  userId: string | null = localStorage.getItem('userId');
  errorMessage = '';

  constructor(private readonly inquiryService: PropertyInquiryService) {}

  ngOnInit(): void {
    this.loadUserInquiries();
  }
  
  loadUserInquiries(): void {
    if (this.userId) {
      this.inquiryService.getInquiriesByUserId(+this.userId).subscribe({
        next: (data) => (this.inquiries = data),
        error: () => (this.errorMessage = 'Failed to load Inquiries.')
      });
    }
  }
}
