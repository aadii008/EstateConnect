import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { PropertyInquiry } from 'src/app/models/property-inquiry.model';
import { PropertyInquiryService } from 'src/app/services/property-inquiry.service';

interface User {
  username: string;
}

interface Property {
  title: string;
}


@Component({
  selector: 'app-admin-view-inquiry',
  templateUrl: './admin-view-inquiry.component.html',
  styleUrls: ['./admin-view-inquiry.component.css']
})
export class AdminViewInquiryComponent implements OnInit{
  inquiries: PropertyInquiry[] = [];

  filteredInquiries:PropertyInquiry[] = [...this.inquiries];
  selectedStatus = '';
  selectedPriority = '';
  searchTerm = '';
  showModal = false;
  selectedInquiryId: number | null = null;
  responseText = '';
  showDeleteConfirm: boolean = false;

  constructor(
    private readonly propertyInquiryService:PropertyInquiryService, 
    private readonly router:Router,
    private readonly activeRoute : ActivatedRoute
    
  ){

  }
  ngOnInit(): void {
    
    this.activeRoute.queryParams.subscribe(params => {
      const priority = params['p'];
      if(priority){
        this.selectedPriority = priority;
      }

      const status = params['s'];
      if(status){
        this.selectedStatus = status;
      }

      this.loadInquiries();
      
    });
  }

  loadInquiries(){
    this.propertyInquiryService.getAllInquiries().subscribe((data) => {
      this.inquiries = data
      this.filteredInquiries = data;
      this.filterInquiries()

    })
  }
  filterInquiries(): void {
    this.filteredInquiries = this.inquiries.filter(inquiry => {
      const matchesStatus = this.selectedStatus ? inquiry.status === this.selectedStatus : true;
      const matchesPriority = this.selectedPriority ? inquiry.priority === this.selectedPriority : true;
      const matchesSearch = this.searchTerm
        ? inquiry.property?.title?.toLowerCase().includes(this.searchTerm.toLowerCase())
        : true;
      return matchesStatus && matchesPriority && matchesSearch;
    });
  }

  openResponseModal(inquiryId: number): void {
    this.selectedInquiryId = inquiryId;
    this.showModal = true;
  }

  closeModal(): void {
    this.showModal = false;
    this.responseText = '';
    this.selectedInquiryId = null;
  }

  submitResponse(): void {
    const inquiry = this.inquiries.find(i => i.inquiryId === this.selectedInquiryId);
    if (inquiry) {
      const updatedInquiry: Partial<PropertyInquiry> = {
        inquiryId: inquiry.inquiryId,
        adminResponse: this.responseText,
        status: 'Responded'
      };
  
      this.propertyInquiryService.updateInquiry(inquiry.inquiryId, updatedInquiry).subscribe({
        next: (updated) => {
          // Update local list
          const index = this.inquiries.findIndex(i => i.inquiryId === updated.inquiryId);
          if (index !== -1) {
            this.inquiries[index] = { ...this.inquiries[index], ...updated };
            this.filterInquiries();
          }
          this.closeModal();
        },
        error: (err) => {
          console.error('Error updating inquiry:', err);
        }
      });
    }
  }
  
  deleteInquiry(): void {
   this.propertyInquiryService.deleteInquiry(this.selectedInquiryId).subscribe(
    (data) => {
      this.loadInquiries();
      this.showDeleteConfirm = false;
      this.selectedInquiryId = null;
    }
   )
  }

  promptDelete(propertyId: number): void {
    this.selectedInquiryId = propertyId;
    this.showDeleteConfirm = true;
  }

  cancelDelete(): void {
    this.showDeleteConfirm = false;
    this.selectedInquiryId = null;
  }
}

 