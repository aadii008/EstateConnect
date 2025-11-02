import { Component, OnInit } from '@angular/core';
import { AuthService } from './services/auth.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  title = 'EstateConnect';

  userRole: string | null = null;

  constructor(private readonly authService: AuthService) {}

  ngOnInit(): void {
    this.authService.userRole$.subscribe(role => {
      this.userRole = role;
    });
  }
}
