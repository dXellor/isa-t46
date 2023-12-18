import { Company } from "../company.model";
import { User } from "../user.model";

export interface Reservation {
    id?: number,
    employee: User,
    companyAdmin: User,
    company: Company,
    note: string,
    status: string,
    dateTime: Date,
    duration: number
}