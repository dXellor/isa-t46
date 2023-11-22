package com.isat46.isaback.dto.company;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.isat46.isaback.dto.address.AddressDto;
import com.isat46.isaback.dto.equipment.EquipmentDto;
import com.isat46.isaback.dto.user.UserDto;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
public class CompanyDto {

    @JsonProperty("id")
    private int id;

    @NotNull
    @NotBlank
    @JsonProperty("name")
    private String name;

    @NotNull
    @NotBlank
    @JsonProperty("description")
    private String description;

    @NotNull
    @JsonProperty("address")
    private AddressDto address;

    @JsonProperty("averageRating")
    private double averageRating;

    @JsonProperty("equipment")
    private List<EquipmentDto> equipment;

    @JsonProperty("admins")
    private List<UserDto> admins;
}
