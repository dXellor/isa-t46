package com.isat46.isaback.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;

@Getter
@Setter
@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "city")
    private String city;

    @Column(name = "country")
    private String country;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "profession")
    private String profession;

    @Column(name = "company_information")
    private String companyInformation;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User user)) return false;
        return Objects.equals(id, user.id) && Objects.equals(email, user.email) && Objects.equals(firstName, user.firstName) && Objects.equals(lastName, user.lastName) && Objects.equals(city, user.city) && Objects.equals(country, user.country) && Objects.equals(phoneNumber, user.phoneNumber) && Objects.equals(profession, user.profession) && Objects.equals(companyInformation, user.companyInformation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, firstName, lastName, city, country, phoneNumber, profession, companyInformation);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", city='" + city + '\'' +
                ", country='" + country + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", profession='" + profession + '\'' +
                ", companyInformation='" + companyInformation + '\'' +
                '}';
    }
}
