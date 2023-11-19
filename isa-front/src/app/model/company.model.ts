import { Address } from "./address.model";

export interface Company {
    id?: number,
    name: string,
    address: Address,
    description: string,
    averageRating?: number,
}