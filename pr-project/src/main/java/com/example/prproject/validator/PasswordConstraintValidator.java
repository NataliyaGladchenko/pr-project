package com.example.prproject.validator;

import com.example.prproject.annotation.ValidPassword;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordConstraintValidator implements ConstraintValidator<ValidPassword, String> {

    private static final String PASSWORD_VALIDATION_PATTERN =
            "(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[~`!@#$%^&*()_\\-+={[}]|:\"'<,>.?\\/]).*";

    @Override
    public void initialize(ValidPassword constraintAnnotation) {
    }

    @Override
    public boolean isValid(String password, ConstraintValidatorContext constraintValidatorContext) {
        boolean isValid = password.matches(PASSWORD_VALIDATION_PATTERN);
        if (!isValid) {
            constraintValidatorContext
                    .buildConstraintViolationWithTemplate("Password is incorrect")
                    .addConstraintViolation();
        }
        return isValid;
    }
}
