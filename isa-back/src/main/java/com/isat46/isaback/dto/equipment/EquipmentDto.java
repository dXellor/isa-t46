package com.isat46.isaback.dto.equipment;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class EquipmentDto {

        @JsonProperty
        private int id;

        @NotBlank
        @JsonProperty
        private String name;

        @NotBlank
        @JsonProperty
        private String description;

        @NotBlank
        @JsonProperty
        private double price;
}
