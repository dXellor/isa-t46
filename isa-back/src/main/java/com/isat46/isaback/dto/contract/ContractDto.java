package com.isat46.isaback.dto.contract;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.isat46.isaback.dto.address.AddressDto;
import com.isat46.isaback.dto.equipment.EquipmentDto;
import com.isat46.isaback.dto.user.UserDto;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Getter
@Setter
public class ContractDto {
    @JsonProperty("id")
    private int id;
    @NotNull
    @NotBlank
    @JsonProperty("hospital")
    private String hospital;
    
    @NotNull
    @NotBlank
    @JsonProperty("company_name")
    private String companyName;

    @NotNull
    @NotBlank
    @JsonProperty("count")
    private int count;

    @NotNull
    @JsonProperty("day")
    private int day;

    @NotNull
    @JsonProperty("equipment")
    private String equipment;
}
