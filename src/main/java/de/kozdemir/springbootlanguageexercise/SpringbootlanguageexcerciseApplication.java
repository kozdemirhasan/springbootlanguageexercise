package de.kozdemir.springbootlanguageexercise;

import de.kozdemir.springbootlanguageexercise.model.*;
import de.kozdemir.springbootlanguageexercise.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class SpringbootlanguageexcerciseApplication implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Value("${db.admin.user.reset}")

    private boolean adminReset;


    public static void main(String[] args) {
        SpringApplication.run(SpringbootlanguageexcerciseApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        if(adminReset) {

            userRepository.deleteAll();
            User user = new User();
            user.setUsername("admin");
            user.setEmail("admin@wbs.com");
            user.setPassword(passwordEncoder.encode("Geheim#1234"));
            user.setRole(UserRole.ADMIN);
            user.setStatus(UserStatus.ACTIVE);
            userRepository.save(user);

            user = new User();
            user.setUsername("h.murat");
            user.setEmail("h.murat@gmail.com");
            user.setPassword(passwordEncoder.encode("Geheim#1234"));
            user.setRole(UserRole.USER);
            user.setStatus(UserStatus.ACTIVE);
            user.setMotherLanguage(Languages.TURKISH);
            user.setTargetLanguage(Languages.GERMAN);
            userRepository.save(user);

            user = new User();
            user.setUsername("hmk");
            user.setEmail("hmk@protonmail.com");
            user.setPassword(passwordEncoder.encode("Geheim#1234"));
            user.setRole(UserRole.USER);
            user.setStatus(UserStatus.BLOCKED);
            user.setMotherLanguage(Languages.ENGLISH);
            user.setTargetLanguage(Languages.SPANISH);
            userRepository.save(user);
        }


    }
}
