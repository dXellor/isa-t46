package com.isat46.isaback.dto.inventory;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.isat46.isaback.dto.equipment.EquipmentDto;
import com.isat46.isaback.model.Company;
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
    @JsonProperty("company")
    private Company company;

    @NotNull
    @JsonProperty("equipment")
    private EquipmentDto equipment;

    @NotNull
    @JsonProperty("count")
    private int count;
}
