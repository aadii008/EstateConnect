import { Component, OnInit } from '@angular/core';
import { PropertyInquiryService } from 'src/app/services/property-inquiry.service';

@Component({
  selector: 'app-admin-control-panel',
  templateUrl: './admin-control-panel.component.html',
  styleUrls: ['./admin-control-panel.component.css']
})
export class AdminControlPanelComponent implements OnInit {

  adminData : any;

  constructor(
    private readonly propertyInquiryService : PropertyInquiryService
  ) { }

  ngOnInit(): void {
    this.loadData()
  }

  loadData(){
    this.propertyInquiryService.getAdminControlPanelData().subscribe(
      (data) => {
        this.adminData = data;
        console.log(this.adminData);
        
      }
    )
  }

}
