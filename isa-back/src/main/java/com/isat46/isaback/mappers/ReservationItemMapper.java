package com.isat46.isaback.mappers;

import com.isat46.isaback.dto.reservation.ReservationItemDto;
import com.isat46.isaback.model.ReservationItem;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ReservationItemMapper {

    private static ModelMapper modelMapper;

    @Autowired
    public ReservationItemMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public static ReservationItemDto ReservationItemToReservationItemDto(ReservationItem reservationItem){
        return modelMapper.map(reservationItem, ReservationItemDto.class);
    }

    public static ReservationItem ReservationItemDtoToReservationItem(ReservationItemDto reservationItemDto){
        return modelMapper.map(reservationItemDto, ReservationItem.class);
    }

    public static List<ReservationItemDto> ReservationItemsToReservationItemDtos(List<ReservationItem> items){
        List<ReservationItemDto> itemDtos = new ArrayList<ReservationItemDto>();
        for(ReservationItem item : items){
            itemDtos.add(ReservationItemToReservationItemDto(item));
        }
        return itemDtos;
    }
}
