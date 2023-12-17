package com.isat46.isaback.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.isat46.isaback.model.Role;
import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import com.isat46.isaback.model.User;

import java.util.List;

@Getter
@Setter
public class UserDto {

    @JsonProperty("id")
    private Integer id;

    @Email
    @NotNull
    @NotBlank
    @JsonProperty("email")
    private String email;

    @NotNull
    @NotBlank
    @JsonProperty("firstName")
    private String firstName;

    @NotNull
    @NotBlank
    @JsonProperty("lastName")
    private String lastName;

    @JsonProperty("city")
    private String city;

    @JsonProperty("country")
    private String country;

    @Pattern(regexp = "^[\\+]?[(]?[0-9]{3}[)]?[-\\s\\.]?[0-9]{3}[-\\s\\.]?[0-9]{4,6}$") //copied from https://ihateregex.io/expr/phone/ (added double \)
    @JsonProperty("phoneNumber")
    private String phoneNumber;

    @JsonProperty("profession")
    private String profession;

    @JsonProperty("companyInformation")
    private String companyInformation;

    @JsonProperty("roles")
    private Role[] roles;

    public UserDto(){}
    public UserDto(User user){
        this(user.getId(), user.getEmail(), user.getFirstName(), user.getLastName(), user.getCity(), user.getCountry(),
                user.getPhoneNumber(), user.getProfession(), user.getCompanyInformation());
    }

    public UserDto(Integer id, String email, String firstName, String lastName, String city, String country, String phoneNumber, String profession, String companyInformation) {
        this.id = id;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.city = city;
        this.country = country;
        this.phoneNumber = phoneNumber;
        this.profession = profession;
        this.companyInformation = companyInformation;
    }
}
