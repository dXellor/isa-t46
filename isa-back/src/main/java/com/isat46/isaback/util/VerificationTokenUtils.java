package com.isat46.isaback.util;

import java.util.Random;

public class VerificationTokenUtils {

    public static String emailTemplate = """
            <html>
                <head>
                    <style>
                        .inline {
                          display: inline;
                        }
                        
                        .link-button {
                          background: none;
                          border: none;
                          color: blue;
                          text-decoration: underline;
                          cursor: pointer;
                          font-size: 1em;
                          font-family: serif;
                        }
                        .link-button:focus {
                          outline: none;
                        }
                        .link-button:active {
                          color:red;
                        }
                    </style>
                </head>
                <body>
                    <h2>Greetings %s %s,</h2>
                    <p>Verify your account by pressing this <a href="%s">link</a></p>                    
                </body>
            </html>
            """;

    public static String getSaltString() {
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 18) {
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        return saltStr;
    }
}
