import { Component } from '@angular/core';
import { User } from 'src/app/model/user.model';
import { UserService } from 'src/app/service/user.service';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { EditUserRequest } from 'src/app/model/edit-user-request.model'
import { MessageService } from 'primeng/api';

@Component({
  selector: 'app-user-edit',
  templateUrl: './user-edit.component.html',
  styleUrls: ['./user-edit.component.css'],
  providers: [MessageService],
})
export class UserEditComponent {
  user: User;
  public editUserForm: FormGroup;
  dataLoaded: boolean = false;

  constructor(private fb: FormBuilder, private userService: UserService, private messageService: MessageService) { }

  ngOnInit(): void {
    this.loadUser();
  }

  loadUser() {
    this.userService.getUser().subscribe({
      next: (result: User) => {
        this.user = result;
        this.editUserForm = this.fb.group({
          email: [this.user.email, [Validators.required, Validators.email]],
          firstName: [this.user.firstName, Validators.required],
          lastName: [this.user.lastName, Validators.required],
          city: [this.user.city],
          country: [this.user.country],
          phoneNumber: [this.user.phoneNumber, Validators.required],
          profession: [this.user.profession],
          companyInformation: [this.user.companyInformation],
        })
        this.dataLoaded = true;
      },
      error: (err: any) => {
        this.messageService.add({ severity: "error", summary: "Loading user profile failed.." })
      }
    });
  }

  updateUser() {
    let registerRequest: EditUserRequest = this.editUserForm.value;
    registerRequest.id = this.user.id;
    this.userService.updateUser(registerRequest).subscribe({
      next: (result: User) => {
        this.user = result;
        this.messageService.add({ severity: "success", summary: "Profile sucessfully updated." });
      },
      error: (err: any) => {
        this.messageService.add({ severity: "error", summary: "Profile update failed." })
      }
    });
  }
}


