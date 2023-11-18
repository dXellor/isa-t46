import { inject } from '@angular/core';
import { CanActivateFn } from '@angular/router';
import { AuthService } from '../service/auth.service';
import { UserService } from '../service/user.service';

export const authGuard: CanActivateFn = (route, state) => {
  const userService = inject(UserService);
  let returnValue = false; 
  userService.getUser().subscribe({
    next: (user) => {
      returnValue = true;
    }
  });

  return returnValue;
};
