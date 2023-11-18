package com.isat46.isaback.model;

import com.isat46.isaback.util.VerificationTokenUtils;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "verification_token")
public class VerificationToken {

    private static final int EXPIRES_IN = 3600000;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "user_email", unique = true, nullable = false)
    private String email;

    @Column(name = "token", nullable = false)
    private String token;

    @Column(name = "expiration_date", nullable = false)
    private Date expirationDate;

    @Column(name = "redeemed", nullable = false)
    private Boolean redeemed = false;

    public void generateExpirationDate(){
        this.expirationDate = new Date(new Date().getTime() + EXPIRES_IN);
    }

    public VerificationToken(){}

    public VerificationToken(User user){
        this.email = user.getEmail();
        this.redeemed = false;
        this.generateExpirationDate();
        this.token = VerificationTokenUtils.getSaltString();
    }
}
