export const roleRoutes: { [role: string]: string } = {
  'ROLE_USER': '/user-home',
  'ROLE_COMPADMIN': '/company-profile',
  'ROLE_SYSADMIN': '/manage-companies'
};

export interface UserRole {
  id?: number,
  name: string,
  authority: string
}

export interface User{
    id?: number,
    email: string,
    firstName: string,
    lastName: string,
    city: string,
    country: string,
    phoneNumber: string,
    profession: string,
    companyInformation: string,
    roles?: UserRole[],
    pendingPasswordReset?: boolean,
}
