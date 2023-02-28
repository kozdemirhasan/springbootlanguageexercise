package de.kozdemir.springbootlanguageexercise.service;


import de.kozdemir.springbootlanguageexercise.model.User;
import de.kozdemir.springbootlanguageexercise.model.UserStatus;
import de.kozdemir.springbootlanguageexercise.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LoginService loginService;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<User> opt = userRepository.findByUsernameIgnoreCase(username);

        if(opt.isPresent()) {
            User u = opt.get();

            loginService.doLogIn(u); //

            return org.springframework.security.core.userdetails.User
                    .withUsername(u.getUsername())
                    .password(u.getPassword())
                    .roles(u.getRole().toString())
                    .disabled(!u.getStatus().equals(UserStatus.ACTIVE))
                    .build();
        };

        throw new RuntimeException("User nicht gefunden");
    }
}
