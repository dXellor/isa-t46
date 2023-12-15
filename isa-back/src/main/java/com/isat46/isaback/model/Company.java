package com.isat46.isaback.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "company")
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name", nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.EAGER, cascade= CascadeType.MERGE)
    @JoinColumn(name = "address_id")
    private Address address;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "average_rating", nullable = false)
    private double averageRating;

    @ManyToMany
    @LazyCollection(LazyCollectionOption.FALSE)
    @JoinTable(name = "company_equipment",
            joinColumns = @JoinColumn(name = "company_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "equipment_id", referencedColumnName = "id"))
    private List<Equipment> equipment;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "company_admin",
            joinColumns = @JoinColumn(name = "company_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "admin_id", referencedColumnName = "id"))
    private List<User> admins;
}
