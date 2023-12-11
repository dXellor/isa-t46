import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { LoginRequest } from 'src/app/model/auth/login-request.model';
import { AuthService } from 'src/app/service/auth.service';
import { UserService } from 'src/app/service/user.service';
import {MessageService} from "primeng/api";

@Component({
  selector: 'app-login-view',
  templateUrl: './login-view.component.html',
  styleUrls: ['./login-view.component.css'],
  providers: [MessageService],
})
export class LoginViewComponent {

  public loginForm: FormGroup;

  constructor(private fb: FormBuilder, private authService: AuthService, private userService: UserService, private router: Router, private messageService: MessageService){
    this.loginForm = this.fb.group({
      email: ['', [Validators.email, Validators.required]],
      password: ['', Validators.required],
    })
  }

  login(){
    let loginRequest: LoginRequest = {
      email: this.loginForm.value.email,
      password: this.loginForm.value.password
    }
    this.authService.login(loginRequest).subscribe({
      next: (token) => {
        window.localStorage.setItem('jwt', token.accessToken);
        this.userService.setLoggedInUser();
        this.messageService.add({severity:'success', summary:'Successfully signed in'});
        setTimeout(() =>{
          this.router.navigate(['/']);
        }, 1000);
      },

      error: (err) => {
        this.loginForm.get('password').setValue('')
        this.messageService.add({severity:'error', summary:'Invalid Credentials', detail:'Username or password are incorrect'});
      }
    });
  }
}
