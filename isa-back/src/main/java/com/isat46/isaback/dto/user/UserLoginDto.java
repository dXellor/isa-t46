package com.isat46.isaback.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class UserLoginDto {

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
}
