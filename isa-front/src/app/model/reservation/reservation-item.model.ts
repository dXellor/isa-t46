import { InventoryItem } from "../inventory.model";

export interface ReservationItem {
    id?: number,
    inventoryItem: InventoryItem,
    count: number
}