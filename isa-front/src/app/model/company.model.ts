import { Address } from "./address.model";
import { User } from "./user.model";

export interface Company {
    id?: number,
    name: string,
    address: Address,
    description: string,
    averageRating?: number,
    admins?: User[]
}