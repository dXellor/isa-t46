import { Component } from '@angular/core';
import { ActivatedRoute, ParamMap, Router } from '@angular/router';
import { AuthService } from 'src/app/service/auth.service';

@Component({
  selector: 'app-verification-view',
  templateUrl: './verification-view.component.html',
  styleUrls: ['./verification-view.component.css']
})
export class VerificationViewComponent {

  public displayText: string = 'Verifying your account...';
  constructor(private router: Router, private route: ActivatedRoute, private authService: AuthService){}

  ngOnInit(): void{
    this.route.paramMap.subscribe((params: ParamMap) => {
      let verif_token = params.get('token');
      this.authService.verify(verif_token).subscribe({
        next: (user) => {
          this.displayText = 'Account verified successfuly. Redirecting to login page...'
          setTimeout(() => {
            this.router.navigate(['/login']);
          }, 2000);
        },

        error: (err) => {
          this.displayText = 'Account verifification failed. Request new verification token.'
        }
      });
    });
  }
}
