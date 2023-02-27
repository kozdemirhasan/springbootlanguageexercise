package de.kozdemir.springbootlanguageexercise.service;


import de.kozdemir.springbootlanguageexercise.model.User;
import org.springframework.stereotype.Service;

import java.io.Serializable;


@Service
public class LoginService implements Serializable {

    private User user;


    public User getUser() {
        return user;
    }

    public void doLogIn(User user) {
        this.user = user;
    }

    public void doLogOut() {
        this.user = null;
    }

    public boolean isLoggedIn() {
        return this.user != null;
    }
}
