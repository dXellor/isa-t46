package com.isat46.isaback.dto.reservation;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OutOfOrderReservationDto {

    @JsonProperty("reservation")
    private ReservationDto reservation;

    @JsonProperty("reservation_items")
    private List<ReservationItemDto> reservationItems;
}
