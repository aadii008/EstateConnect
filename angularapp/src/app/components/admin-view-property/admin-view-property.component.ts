import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Property } from 'src/app/models/property.model';
import { PropertyService } from 'src/app/services/property.service';

@Component({
  selector: 'app-admin-view-property',
  templateUrl: './admin-view-property.component.html',
  styleUrls: ['./admin-view-property.component.css']
})
export class AdminViewPropertyComponent implements OnInit {

  properties: Property[] = [];
  filteredProperties: Property[] = [];
  errorMessage = '';

  searchTerm: string = '';
  selectedType: string = '';
  propertyTypes: string[] = [];

  showDeleteConfirm: boolean = false;
  selectedPropertyId: number | null = null;
 
  constructor(
    private readonly propertyService: PropertyService,
    private readonly router: Router
  ) {}

  ngOnInit(): void {
    this.loadProperties();
  }


  loadProperties(): void {
    this.propertyService.getAllProperties().subscribe({
      next: (data) => {
        this.properties = data;
        this.propertyTypes = [...new Set(data.map(p => p.type))];
        this.applyFilters();
      },
      error: () => (this.errorMessage = 'Failed to load properties.')
    });
  }

  applyFilters(): void {
    this.filteredProperties = this.properties.filter(property => {
      const matchesTitle = property.title.toLowerCase().includes(this.searchTerm.toLowerCase());
      const matchesType = this.selectedType ? property.type === this.selectedType : true;
      return matchesTitle && matchesType;
    });
  }

  editProperty(propertyId: number): void {
    this.router.navigate(['/edit-property', propertyId]);
  }

  promptDelete(propertyId: number): void {
    this.selectedPropertyId = propertyId;
    this.showDeleteConfirm = true;
  }

  confirmDelete(): void {
    if (this.selectedPropertyId !== null) {
      this.propertyService.deleteProperty(this.selectedPropertyId).subscribe({
        next: () => {
          this.loadProperties();
          this.showDeleteConfirm = false;
          this.selectedPropertyId = null;
        },
        error: () => {
          this.errorMessage = 'Cannot delete property because an associated inquiry already exists. Please resolve or remove the inquiry before attempting deletion.';
          this.showDeleteConfirm = false;
          this.selectedPropertyId = null;
        }
      });
     }
  }

  cancelDelete(): void {
    this.showDeleteConfirm = false;
    this.selectedPropertyId = null;
  }
}


