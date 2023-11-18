export interface RegisterRequest{
    email: string,
    password: string,
    repeatedPassword: string,
    firstName: string,
    lastName: string,
    city?: string,
    country?: string,
    phoneNumber: string,
    profession: string,
    companyInformation: string
}