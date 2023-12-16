package com.isat46.isaback.dto.reservation;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class AppointmentCreationDto {

    @NotNull
    @JsonProperty("admin_id")
    private int companyAdminId;

    @NotNull
    @JsonProperty("dateTime")
    private LocalDateTime dateTime;

    //Duration in minutes
    @NotNull
    @JsonProperty("duration")
    private long duration;
}
