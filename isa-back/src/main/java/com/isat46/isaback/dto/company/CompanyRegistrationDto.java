package com.isat46.isaback.dto.company;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.isat46.isaback.dto.address.AddressDto;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class CompanyRegistrationDto {

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
}
