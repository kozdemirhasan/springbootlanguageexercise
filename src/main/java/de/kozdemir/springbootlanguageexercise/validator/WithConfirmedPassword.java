package de.kozdemir.springbootlanguageexercise.validator;


import de.kozdemir.springbootlanguageexercise.annotation.PasswordMatch;

@PasswordMatch
public interface WithConfirmedPassword {

    String getPassword();

    String getPasswordConfirmation();
}
