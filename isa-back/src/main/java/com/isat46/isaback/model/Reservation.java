package com.isat46.isaback.model;

import com.isat46.isaback.model.enums.ReservationStatus;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "reservation")
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "employee_id")
    private User employee;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "admin_id")
    private User companyAdmin;

    @Column(name = "note")
    private String note;

    @Column(name = "status")
    private ReservationStatus status;

    @Column(name = "date_time")
    private LocalDateTime dateTime;

    //Duration in minutes
    @Column(name = "duration")
    private long duration;
}
