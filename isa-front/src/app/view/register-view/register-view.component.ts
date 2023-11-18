import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { RegisterRequest } from 'src/app/model/auth/register-request.model';
import { AuthService } from 'src/app/service/auth.service';

@Component({
  selector: 'app-register-view',
  templateUrl: './register-view.component.html',
  styleUrls: ['./register-view.component.css']
})
export class RegisterViewComponent {

  public registerForm: FormGroup;

  constructor(private fb: FormBuilder, private authService: AuthService, private router: Router){
    this.registerForm = this.fb.group({
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.min(8)]],
      repeatedPassword: ['', Validators.required],
      firstName: ['', Validators.required],
      lastName: ['', Validators.required],
      city: [''],
      country: [''],
      phoneNumber: ['', Validators.required],
      profession: [''],
      companyInformation: [''],
    });
  }

  register(): void{
    let registerRequest: RegisterRequest = this.registerForm.value;
    this.authService.register(registerRequest).subscribe({
      next: (user) => {
        window.alert('Your registration request is successful. Check your email for verification link');
        this.router.navigate(['/login']);
      },

      error: (err) => {
        window.alert('Invalid request (fix later)');
      }
    });
  }
}
