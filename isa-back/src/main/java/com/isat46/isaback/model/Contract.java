package com.isat46.isaback.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalTime;

@Getter
@Setter
@Entity
@Table(name = "contract")
public class Contract {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    @Column(name="hospital", nullable = false)
    private String hospital;
    
    @Column(name = "company_name", nullable = false)
    private String companyName;

    @Column(name = "count", nullable = false)
    private int count;

    @Column(name = "day", nullable = false)
    private int day;

    @Column(name = "equipment", nullable = false)
    private String equipment;
    
    
}
