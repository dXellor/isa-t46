package com.isat46.isaback.dto.reservation;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.isat46.isaback.dto.company.CompanyInfoDto;
import com.isat46.isaback.dto.user.UserDto;
import com.isat46.isaback.model.enums.ReservationStatus;
import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Getter
@Setter
public class ReservationDto {

    @JsonProperty("id")
    private Integer id;

    @JsonProperty("employee")
    private UserDto employee;

    @JsonProperty("companyAdmin")
    private UserDto companyAdmin;

    @JsonProperty("company")
    private CompanyInfoDto company;

    @JsonProperty("note")
    private String note;

    @JsonProperty("status")
    private String status;

    @NotNull
    @JsonProperty("dateTime")
    private LocalDateTime dateTime;

    //Duration in minutes
    @NotNull
    @JsonProperty("duration")
    private long duration;


}
