import { Reservation } from "./reservation.model";
import { ReservationItem } from "./reservation/reservation-item.model";

export interface OutOfOrderReservation {
    reservation: Reservation,
    reservation_items: ReservationItem[]
  }