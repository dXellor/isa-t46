package com.isat46.isaback.dto.inventory;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.isat46.isaback.model.Equipment;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class InventoryItemDto
{
    @JsonProperty("id")
    private int id;

    @NotNull
    @NotBlank
    @JsonProperty("equipment")
    private Equipment equipment;

    @NotNull
    @NotBlank
    @JsonProperty("count")
    private int count;
}
