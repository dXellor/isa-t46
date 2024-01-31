import { CanActivateFn } from '@angular/router';
import { User } from '../model/user.model';
import { UserService } from '../service/user.service';
import { inject } from '@angular/core';

export const compAdminGuard: CanActivateFn = (route, state) => {
  const userService = inject(UserService);
  const user: User = userService.getCurrentUser();
  return user !== undefined && user !== null && user.roles[0].id === 3;
};
