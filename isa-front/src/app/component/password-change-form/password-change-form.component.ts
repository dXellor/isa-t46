import { Component, EventEmitter, Output } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MessageService } from 'primeng/api';
import { User } from 'src/app/model/user.model';
import { AuthService } from 'src/app/service/auth.service';
import { UserService } from 'src/app/service/user.service';

@Component({
  selector: 'app-password-change-form',
  templateUrl: './password-change-form.component.html',
  styleUrls: ['./password-change-form.component.css'],
  providers: [MessageService],
})
export class PasswordChangeFormComponent {

  @Output() passwordChanged = new EventEmitter<null>();
  public passwordChangeForm: FormGroup;

  constructor(private fb: FormBuilder, private authService: AuthService, private userService: UserService, private messageService: MessageService){
    this.passwordChangeForm = this.fb.group({
      password: ['', Validators.required],
    })
  }

  changePassword(){
    let newPassword = this.passwordChangeForm.value.password
    this.authService.changePassword(newPassword).subscribe({
      next: () => {
        this.userService.passwordChanged();
        this.messageService.add({severity:'success', summary:'Successfully changed password'});
        this.passwordChanged.emit();
      },

      error: (err) => {
        this.passwordChangeForm.get('password').setValue('');
        this.messageService.add({severity:'error', summary:'There was an error'});
      }
    });
  }

}
