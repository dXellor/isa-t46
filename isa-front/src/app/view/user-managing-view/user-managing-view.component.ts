import { Component, OnInit } from '@angular/core';
import { User } from '../../model/user.model';
import { FormGroup } from '@angular/forms';
import { UserService } from '../../service/user.service'
import { PagedResult } from '../../model/paged-result.model';

@Component({
  selector: 'app-user-managing-view',
  templateUrl: './user-managing-view.component.html',
  styleUrls: ['./user-managing-view.component.css']
})
export class UserManagingViewComponent implements OnInit {
  public users: User[];
  public showCompanyAdminForm: boolean;

  constructor(private userService: UserService) { }

  ngOnInit(): void {
      this.getAllUsers();
  }

  getAllUsers(): void {
    this.userService.getAll().subscribe({
      next: (response: PagedResult<User>) => {
        this.users = response.content;
      }
    });
  }

  addNewCompanyAdmin(): void {
    this.showCompanyAdminForm = true;
  }

  companyAdminAdded(): void {
    this.getAllUsers();
    this.showCompanyAdminForm = false;
  }
}
