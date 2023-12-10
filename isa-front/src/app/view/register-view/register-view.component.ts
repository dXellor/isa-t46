import {Component, ViewChild, ViewEncapsulation} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {Router} from '@angular/router';
import {RegisterRequest} from 'src/app/model/auth/register-request.model';
import {AuthService} from 'src/app/service/auth.service';
import {MessageService} from "primeng/api";

@Component({
  selector: 'app-register-view',
  templateUrl: './register-view.component.html',
  styleUrls: ['./register-view.component.css'],
  providers: [MessageService]
})
export class RegisterViewComponent {
  public registerForm: FormGroup;
  currentStep: number = 0;

  constructor(private fb: FormBuilder, private authService: AuthService, private router: Router, private messageService: MessageService){
    this.registerForm = this.fb.group({
      personalInfo: this.fb.group({
        firstName: ['', Validators.required],
        lastName: ['', Validators.required],
        city: [''],
        country: [''],
        phoneNumber: ['', Validators.required],
      }),
      userInfo: this.fb.group({
        email: ['', [Validators.required, Validators.email]],
        password: ['', [Validators.required, Validators.minLength(8)]],
        repeatedPassword: ['', Validators.required],},{validator: this.passwordMatchValidator}),
      companyInfo: this.fb.group({
        profession: [''],
        companyInformation: [''],
      }),
    });
  }

  private isCurrentStepValid(): boolean {
    const currentStepForm = this.registerForm.get(this.getStepName()) as FormGroup;

    if (this.currentStep === 0) {
      if (currentStepForm.get('firstName').hasError('required')) {
        this.showErrorMessage('First name is required.');
        return false;
      }
      if (currentStepForm.get('lastName').hasError('required')) {
        this.showErrorMessage('Last name is required.');
        return false;
      }
      if (currentStepForm.get('phoneNumber').hasError('required')) {
        this.showErrorMessage('Phone number is required.');
        return false;
      }

      return currentStepForm.valid;

    } else if (this.currentStep === 1) {
      if (currentStepForm.get('email').hasError('required')) {
        this.showErrorMessage('Email is required.');
        return false;
      }
      if (currentStepForm.get('email').hasError('email')) {
        this.showErrorMessage('Invalid email format.');
        return false;
      }

      const passwordControl = currentStepForm.get('password');
      const repeatPasswordControl = currentStepForm.get('repeatedPassword');

      if (passwordControl.hasError('required')) {
        this.showErrorMessage('Password is required.');
        return false;
      }

      if (passwordControl.hasError('minlength')) {
        this.showErrorMessage('Password must be at least 8 characters long.');
        return false;
      }

      if (repeatPasswordControl.hasError('required')) {
        this.showErrorMessage('Repeat Password is required.');
        return false;
      }

      if (currentStepForm.hasError('mismatch')) {
        this.showErrorMessage('Password and Repeat Password must match.');
        return false;
      }

      return currentStepForm.valid;
    }
    return false;
  }

  private showErrorMessage(message: string): void {
    this.messageService.clear();
    this.messageService.add({
      severity: 'error',
      summary: 'Validation Error',
      detail: message,
    });
  }
  private getStepName(): string {
    return Object.keys(this.registerForm.controls)[this.currentStep];
  }

  onNextClick(): void {
    if (this.isCurrentStepValid()) {
      this.currentStep++;
    } else {
      return;
    }
  }

  onBackClick(): void {
    this.currentStep--;
  }

  register(): void{
    let registerRequest: RegisterRequest = this.registerForm.value;
    console.log('Request Payload:', registerRequest);
    this.authService.register(registerRequest).subscribe({
      next: (user) => {
        this.messageService.add({severity:'error', summary:'Registered successfully'});
        setTimeout(() =>{
          this.router.navigate(['/login']);
            },1000)
      },

      error: (err) => {
        this.messageService.add({severity:'error', summary:'Invalid registration data'});
      }
    });
  }

  passwordMatchValidator(g: FormGroup) {
    return g.get('password').value === g.get('repeatedPassword').value
        ? null : {'mismatch': true};
  }
}
