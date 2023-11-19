import { Component } from '@angular/core';
import { User } from 'src/app/model/user.model';
import { UserService } from 'src/app/service/user.service';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { EditUserRequest } from 'src/app/model/edit-user-request.model'

@Component({
  selector: 'app-user-edit',
  templateUrl: './user-edit.component.html',
  styleUrls: ['./user-edit.component.css']
})
export class UserEditComponent {
  user: User;
  public editUserForm: FormGroup;

  constructor(private fb: FormBuilder, private userService: UserService){
    this.editUserForm = this.fb.group({
      email: ['', [Validators.required, Validators.email]],
      firstName: ['', Validators.required],
      lastName: ['', Validators.required],
      city: [''],
      country: [''],
      phoneNumber: ['', Validators.required],
      profession: [''],
      companyInformation: [''],
    })
  }

  ngOnInit(): void{
    this.loadUser();
  }

  loadUser(){
    this.userService.getUser().subscribe({
      next: (result: User) => {
        this.user = result;
      },
      error: (err: any) => {
        console.log(err);
      }
    });
  }

  updateUser(){
    let registerRequest: EditUserRequest = this.editUserForm.value;
    registerRequest.id = this.user.id;
    this.userService.updateUser(registerRequest).subscribe({
      next: (result: User) => {
        this.user = result;
      },
      error: (err: any) => {
        console.log(err);
      }
    });
  }
}


