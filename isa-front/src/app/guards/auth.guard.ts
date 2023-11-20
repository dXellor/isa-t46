import { inject } from '@angular/core';
import { CanActivateFn } from '@angular/router';
import { UserService } from '../service/user.service';
import { User } from '../model/user.model';

export const authGuard: CanActivateFn = (route, state) => {
  const userService = inject(UserService);
  const user: User = userService.getCurrentUser();
  return user !== undefined && user !== null;
};
