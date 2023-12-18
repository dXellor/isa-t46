export interface UserRole{
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
