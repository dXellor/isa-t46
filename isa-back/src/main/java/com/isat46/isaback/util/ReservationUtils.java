package com.isat46.isaback.util;

import com.isat46.isaback.dto.reservation.ReservationDto;
import com.isat46.isaback.dto.reservation.ReservationItemDto;

import java.time.LocalDateTime;
import java.util.List;

public class ReservationUtils {

    public static String emailTemplate = """
            <html>
                <head>
                </head>
                <body>
                    <h2>Greetings %s %s,</h2>
                    <p>You have successfully made a reservation. Reservation information is containted in QR code attached to this email.</p>                    
                </body>
            </html>
            """;

    private static String reservationInformationTemplate = """
            Reservation Id: %d
            
            --COMPANY INFORMATION--
            Name: %s
            Description: %s
            Address: %s
            
            --RESERVATION INFORMATION--
            Company admin in charge of reservation: %s %s
            Employee: %s %s
            Date and time: %s
            Duration: %d minutes
            Note: %s
            
            --RESERVATION ITEMS--
            Item | Count | Price
            %s
            """;

    public static String getReservationInformation(ReservationDto reservationDto, List<ReservationItemDto> reservationItems){


        return String.format(reservationInformationTemplate,
                reservationDto.getId(),
                reservationDto.getCompany().getName(),
                reservationDto.getCompany().getDescription(),
                reservationDto.getCompany().getAddress().toString(),
                reservationDto.getCompanyAdmin().getFirstName(),
                reservationDto.getCompanyAdmin().getLastName(),
                reservationDto.getEmployee().getFirstName(),
                reservationDto.getEmployee().getLastName(),
                reservationDto.getDateTime().toString(),
                reservationDto.getDuration(),
                reservationDto.getNote().isEmpty() ? "/" : reservationDto.getNote(),
                getReservationItemsInformation(reservationItems));
    }

    private static String getReservationItemsInformation(List<ReservationItemDto> reservationItems){
        StringBuilder sb = new StringBuilder();
        double totalPrice = 0;

        for(ReservationItemDto item : reservationItems){
            sb.append(item.toInformationString());
            sb.append("\n");
            totalPrice += item.getInventoryItem().getEquipment().getPrice() * item.getCount();
        }

        sb.append(String.format("Total price: %.2f $", totalPrice));
        return sb.toString();
    }

    public static Boolean isDateTimeIntertwined(LocalDateTime start1, LocalDateTime end1, LocalDateTime start2, LocalDateTime end2){
        return !(start1.isAfter(end2) || end1.isBefore(start2));
    }
}
