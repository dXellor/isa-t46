export interface EditUserRequest{
    id: number,
    firstName: string,
    lastName: string,
    email: string,
    city?: string,
    country?: string,
    phoneNumber: string,
    profession: string,
    companyInformation: string
}