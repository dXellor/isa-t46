package com.isat46.isaback.dto.reservation;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.isat46.isaback.dto.inventory.InventoryItemDto;
import com.isat46.isaback.model.Equipment;
import com.isat46.isaback.model.InventoryItem;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class ReservationItemDto {

    @JsonProperty("id")
    private int id;

    @NotNull
    @NotBlank
    @JsonProperty("inventoryItem")
    private InventoryItemDto inventoryItem;

    @NotNull
    @NotBlank
    @JsonProperty("count")
    private int count;

    public String toInformationString(){
        return inventoryItem.getEquipment().getName() + " | " + count + " | " + inventoryItem.getEquipment().getPrice() * count + "$";
    }
}
