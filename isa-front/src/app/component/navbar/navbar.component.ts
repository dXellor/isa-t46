import { Component, ElementRef, HostListener, OnInit, Renderer2, ViewChild } from '@angular/core';
import { Router } from '@angular/router';
import { User, UserRole } from 'src/app/model/user.model';
import { UserService } from 'src/app/service/user.service';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit {
  @ViewChild('overlayContainer', { static: false }) overlayContainer: ElementRef | undefined;

  public currentUser: User = null;
  isOverlayOpen: boolean = false;

  constructor(private userService: UserService, private router: Router, private rendered: Renderer2) { }

  @HostListener('document:click', ['$event'])
  handleClick(event: Event) {
    if (this.overlayContainer && !this.overlayContainer.nativeElement.contains(event.target)) {
      this.closeOverlay();
    }
  }

  ngOnInit(): void {
    this.userService.setLoggedInUser();
    this.userService.loggedInUserTrigger.subscribe(user => {
      this.currentUser = user;
    });
  }

  logOut(): void {
    window.localStorage.removeItem('jwt');
    this.userService.setLoggedInUser();
    this.router.navigate(['/']);
  }

  hasRole(selectedRole: string): boolean {
    for (let role of this.currentUser.roles) {
      if (role.name === selectedRole)
        return true;
    }
    return false;
  }
  toggleMenu(event: Event) {
    event.stopPropagation();
    this.isOverlayOpen = !this.isOverlayOpen;
  }

  closeOverlay() {
    this.isOverlayOpen = false;
  }
}
