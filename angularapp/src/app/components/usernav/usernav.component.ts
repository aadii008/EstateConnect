import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/services/auth.service';

@Component({
  selector: 'app-usernav',
  templateUrl: './usernav.component.html',
  styleUrls: ['./usernav.component.css']
})
export class UsernavComponent implements OnInit {

  username : string = "";
  showLogOutMode : boolean = false;

  constructor(private readonly router:Router, private readonly authService : AuthService) { }

  ngOnInit(): void {
    this.username = localStorage.getItem('authenticatedUser') ?? "";
  }

  handleLogOut(){
    this.showLogOutMode = true;
  }

  handleYes(){
    this.authService.clearLocalStorage();
    this.router.navigate(['/login']).then(
      (data) => {
        this.showLogOutMode = false;
      }
    );
  }

  handleNo(){
    console.log("in no method");
    
    this.showLogOutMode = false;
    console.log(this.showLogOutMode);
    
  }

}
