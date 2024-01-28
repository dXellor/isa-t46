package com.isat46.isaback.util;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PositionSimulatorCommand {

    private String command;
    private int reservationId;
    private float startLatitude;
    private float startLongitude;
    private float endLatitude;
    private float endLongitude;
    private float delay;

    public PositionSimulatorCommand(String command, int reservationId, float startLatitude, float startLongitude, float endLatitude, float endLongitude, float delay) {
        this.command = command;
        this.reservationId = reservationId;
        this.startLatitude = startLatitude;
        this.startLongitude = startLongitude;
        this.endLatitude = endLatitude;
        this.endLongitude = endLongitude;
        this.delay = delay;
    }

    @Override
    public String toString() {
        return  command + ";" + reservationId + ";" + startLatitude + ";" + startLongitude + ";" + endLatitude + ";" + endLongitude + ";" + delay;
    }
}
