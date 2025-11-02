import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/services/auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  loginForm: FormGroup;
  submitted = false;
  loginError:string = '';

  constructor(
    private readonly fb: FormBuilder,
    private readonly router: Router,
    private readonly authService : AuthService
  ) {
    this.loginForm = this.fb.group({
      email: ['', [Validators.required, Validators.pattern('^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$')]],
    password: ['', [Validators.required]]
    });
  }
  ngOnInit(): void {
    const authenticatedUser = localStorage.getItem('authenticatedUser');

    if(authenticatedUser){
      this.router.navigate(['/home'])
    }
  }

  showPassword: boolean = false;
  togglePasswordVisibility() {
    this.showPassword = !this.showPassword;
  }

  onSubmit(): void {
    this.submitted = true;
    this.loginError = '';

    if (this.loginForm.valid) {
      this.authService.login(this.loginForm.value).subscribe({
        next: () => {
          this.router.navigate(['/home']);
        },
        error: (err) => {
          if (err.status === 401) {
            this.loginError = 'Invalid email or password.';
          } else if (err.status === 404) {
            this.loginError = 'User not found.';
          } else {
            this.loginError = 'Something went wrong. Please try again.';
          }
        }
      });
    }
  }
}