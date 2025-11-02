import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/services/auth.service';

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css']
})
export class SignupComponent implements OnInit {
  signupForm: FormGroup;
  submitted = false;
  showSuccessModal = false;
  showPopUp = false;
  popUpMessage : string = "";
  showPassword: boolean = false;

  constructor(private readonly fb: FormBuilder, private readonly authService: AuthService, private readonly router: Router) {
    this.signupForm = this.fb.group({
      username: ['', Validators.required],
      email: ['', [Validators.required, Validators.pattern('^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$')]],
      mobileNumber: ['', [Validators.required, Validators.pattern('^(?!0{10})[6-9]\\d{9}$')]],
      password: ['', [Validators.required, Validators.pattern("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[^A-Za-z0-9]).{8,}$")]],
      confirmPassword: ['', Validators.required],
      userRole: ['', Validators.required]
    }, {
      validator: this.mustMatch('password', 'confirmPassword')
    });
  }

  ngOnInit(): void {
    const authenticatedUser = localStorage.getItem('authenticatedUser');

    if(authenticatedUser){
      this.router.navigate(['/home'])
    }
  }
  
  togglePasswordVisibility() {
    this.showPassword = !this.showPassword;
  }

  
  get f() {
    return this.signupForm.controls;
  }

  mustMatch(password: string, confirmPassword: string) {
    return (formGroup: FormGroup) => {
      const passControl = formGroup.controls[password];
      const confirmControl = formGroup.controls[confirmPassword];

      if (confirmControl.errors && !confirmControl.errors['mustMatch']) {
        return;
      }

      if (passControl.value !== confirmControl.value) {
        confirmControl.setErrors({ mustMatch: true });
      } else {
        confirmControl.setErrors(null);
      }
    };
  }

  onSubmit() {
    this.submitted = true;
    if (this.signupForm.invalid) return;

    this.authService.register(this.signupForm.value).subscribe({
      next: () => {
        this.router.navigate(['/login'])
      },
      error: (err) => {
        this.popUpMessage = 'User Already Exists..!';
        this.showPopUp = true;   
        }
      }
    )
  }

  onOkClick(){
    this.showPopUp = false;
    this.popUpMessage = "";
  }
}