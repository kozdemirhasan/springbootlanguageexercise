package de.kozdemir.springbootlanguageexercise.model;


import de.kozdemir.springbootlanguageexercise.annotation.PasswordMatch;
import de.kozdemir.springbootlanguageexercise.validator.WithConfirmedPassword;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@PasswordMatch
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserDto implements WithConfirmedPassword {

    @NotEmpty
    private String username;

    @Email(regexp = ".*")
    @NotEmpty
    private String email;



    private Languages motherLanguage;


    private Languages targetLanguage;

    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[#$@!%&*?])[A-Za-z\\d#$@!%&*?]{8,25}$", message = "Passwort ist ungültig.")
    private String password;

    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[#$@!%&*?])[A-Za-z\\d#$@!%&*?]{8,25}$", message = "Passwort ist ungültig.")
    private String passwordConfirmation;

    public User convert(PasswordEncoder passwordEncoder) {
        User user = new User();
        user.setUsername(username.toLowerCase());
        user.setEmail(email.toLowerCase());
        user.setPassword(passwordEncoder.encode(password));
        user.setRole(UserRole.USER);
        user.setMotherLanguage(motherLanguage);
        user.setTargetLanguage(targetLanguage);
        user.setStatus(UserStatus.CREATED);
        return user;
    }

}
