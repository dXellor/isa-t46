package com.isat46.isaback.dto.equipment;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class EquipmentDto {

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
        @NotBlank
        @JsonProperty("type")
        private String type;

        @NotNull
        @Min(1)
        @NotBlank
        @JsonProperty("price")
        private double price;
}
