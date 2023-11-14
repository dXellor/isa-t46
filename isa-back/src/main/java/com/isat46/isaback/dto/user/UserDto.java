package com.isat46.isaback.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

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
}
