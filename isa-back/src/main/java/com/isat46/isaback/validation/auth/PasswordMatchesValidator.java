package com.isat46.isaback.validation.auth;

import com.isat46.isaback.dto.user.UserRegistrationDto;
import com.isat46.isaback.validation.auth.annotation.PasswordMatches;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, Object> {
    @Override
    public void initialize(PasswordMatches constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
        UserRegistrationDto userRegistrationDto = (UserRegistrationDto) o;
        return userRegistrationDto.getPassword().equals(userRegistrationDto.getRepeatedPassword());
    }
}
