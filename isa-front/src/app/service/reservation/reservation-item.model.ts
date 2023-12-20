import { InventoryItem } from "src/app/model/inventory.model"

export interface ReservationItem {
    id?: number,
    inventoryItem: InventoryItem
    count: number,
}