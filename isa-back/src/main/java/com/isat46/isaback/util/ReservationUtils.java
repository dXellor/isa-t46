package com.isat46.isaback.util;

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
}
