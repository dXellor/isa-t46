package com.isat46.isaback.dto.reservation;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReservationQRCodeDto {

    @JsonProperty("qrCodeImageData")
    private byte[] qrCodeImageData;

    public ReservationQRCodeDto(byte[] qrCodeImageData) {
        this.qrCodeImageData = qrCodeImageData;
    }


}
