package de.kozdemir.springbootlanguageexercise.controller;

import de.kozdemir.springbootlanguageexercise.model.User;
import de.kozdemir.springbootlanguageexercise.model.UserDto;
import de.kozdemir.springbootlanguageexercise.repository.UserRepository;
import de.kozdemir.springbootlanguageexercise.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.mail.MessagingException;

import javax.validation.Valid;


@Controller
@RequestMapping("sess")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LoginService loginService;

    @GetMapping("login")
    public String loginForm(Model model) {
        if (loginService.isLoggedIn())
            return "redirect:/todos";
        return "login-form";
    }

    @PostMapping("login")
    public String login(String email, String password, Model model) {
        User user = userRepository.findByEmail(email);
        if (user != null && user.getPassword().equals(password)) {
            loginService.doLogIn(user);
            return "redirect:/todos";
        }
        model.addAttribute("error", true);
        return "login-form";
    }


    @GetMapping("logout")
    public String logout(Model model) {
        loginService.doLogOut();
        model.addAttribute("data", "Abgemeldet");
        return "redirect:/sess/login";
    }
}