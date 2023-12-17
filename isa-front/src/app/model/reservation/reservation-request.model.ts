import { ReservationItem } from "./reservation-item.model";

export interface ReservationRequest {
    reservation_id: number,
    reservation_items: ReservationItem[],
    note: string
}