package com.isat46.isaback.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.isat46.isaback.validation.auth.annotation.PasswordMatches;
import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@PasswordMatches
public class UserRegistrationDto {

    @NotNull
    @NotBlank
    @Email(message = "invalid email address")
    @JsonProperty("email")
    private String email;

    @NotNull
    @NotBlank
    @Length(min = 8, message = "Password must be at least 8 characters long")
    @JsonProperty("password")
    private String password;

    @NotNull
    @NotBlank
    @JsonProperty("repeatedPassword")
    private String repeatedPassword;

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

    @Pattern(regexp = "^[\\+]?[(]?[0-9]{3}[)]?[-\\s\\.]?[0-9]{3}[-\\s\\.]?[0-9]{4,6}$", message = "Invalid phone number") //copied from https://ihateregex.io/expr/phone/ (added double \)
    @JsonProperty("phoneNumber")
    private String phoneNumber;

    @JsonProperty("profession")
    private String profession;

    @JsonProperty("companyInformation")
    private String companyInformation;
}
