import { Company } from "./company.model";
import { Equipment } from "./equipment.model";

export interface InventoryItem {
    id?: number;
    equipment: Equipment,
    count: number,
    company: Company
}