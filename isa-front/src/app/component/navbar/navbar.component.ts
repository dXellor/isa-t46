import { Component } from '@angular/core';
import { User } from 'src/app/model/user.model';
import { UserService } from 'src/app/service/user.service';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent {

  public currentUser: User = null;

  constructor(private userService: UserService){}

  ngOnInit(): void{
    this.userService.loggedInUserTrigger.subscribe(user => {
      this.currentUser = user;
    });
  }

  logOut(): void{
    window.localStorage.removeItem('jwt');
    this.userService.setLoggedInUser();
  }
}
