import { Address } from "./address.model";
import { Equipment } from "./equipment.model";
import { User } from "./user.model";

export interface Company {
    id?: number,
    name: string,
    address: Address,
    description: string,
    averageRating?: number,
    admins?: User[],
    equipment?: Equipment[]
    startWork: string,
    endWork: string
}