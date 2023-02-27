package de.kozdemir.springbootlanguageexercise.validator;

import de.kozdemir.springbootlanguageexercise.annotation.PasswordMatch;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordMatchValidator implements ConstraintValidator<PasswordMatch, WithConfirmedPassword> {

    public boolean isValid(WithConfirmedPassword obj, ConstraintValidatorContext context) {

        if(obj.getPassword() == null || !obj.getPassword().equals(obj.getPasswordConfirmation())) {
            return false;
        }

        return true;
    }
}