package com.isat46.isaback.dto.reservation;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ReservationCreationDto {

    @NotNull
    @JsonProperty("reservation_id")
    private int reservationId;

    @NotNull
    @JsonProperty("reservation_items")
    private List<ReservationItemDto> reservationItems;

    @NotNull
    @JsonProperty("note")
    private String note;
}
