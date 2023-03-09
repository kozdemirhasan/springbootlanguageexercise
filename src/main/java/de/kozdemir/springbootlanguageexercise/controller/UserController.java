package de.kozdemir.springbootlanguageexercise.controller;

import de.kozdemir.springbootlanguageexercise.model.*;
import de.kozdemir.springbootlanguageexercise.repository.TokenRepository;
import de.kozdemir.springbootlanguageexercise.repository.UserRepository;
import de.kozdemir.springbootlanguageexercise.service.CustomEmailService;
import de.kozdemir.springbootlanguageexercise.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Controller
public class UserController {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private CustomEmailService emailService;
    @Autowired
    private LoginService loginService;


    @GetMapping("")
    public String index(Model model) {
        model.addAttribute("userName", loginService.getUser().getUsername());
        return "standart";
    }

    @GetMapping({"login", "login/{sub}"})
    public String login(@PathVariable Optional<String> sub, Model model) {
        sub.ifPresent(s -> {
            model.addAttribute(s, true);
        });

        return "login";
    }

    @GetMapping("mail")
    public String sendMail(Model model) {
        emailService.sendSimpleEmail("p.parker@shield.org", "Du bist raus...", "Das reicht. Du bist bei uns raus...");
        return "redirect:/";
    }

    @GetMapping("forgot")
    public String forgotForm(UserDto userDto, Model model) {
        model.addAttribute("userDto", userDto);
        return "forgot";
    }

    @PostMapping("forgot")
    public String sendForgotEmail(UserDto userDto, Model model) throws MessagingException {

        // Wenn nicht vorhanden, soll eine Exception geworfen werden
        Optional<User> opt = userRepository.findByEmailIgnoreCase(userDto.getEmail());
        if (opt.isPresent()) {
            User user = opt.get(); // get liefert bei einem leeren Optional eine Exception
            Token token = new Token(user, Token.TokenType.PASSWORD);
            tokenRepository.save(token);
            emailService.sendHtmlForgotEmail(user, token.getId());
        }

        model.addAttribute("sent", true);
        return "forgot";
    }

    @GetMapping("forgot/reset")
    public String checkForgotToken(@RequestParam("token") String tokenStr, Model model) {
        try {
            Optional<Token> opt = tokenRepository.findByIdAndType(UUID.fromString(tokenStr), Token.TokenType.PASSWORD);

            if (opt.isPresent()) {
                Token token = opt.get();
                User user = token.getUser();
                UserDto userDto = new UserDto(); // TODO: Konvertierung von user nach userDto einbauen
                userDto.setUsername(user.getUsername());
                userDto.setEmail(user.getEmail());
                model.addAttribute("userDto", userDto);
            } else {
                throw new RuntimeException("Token nicht gefunden.");
            }
        } catch (Exception e) {
            model.addAttribute("error", true);
        }

        return "reset-password";
    }

    @PostMapping("forgot/reset")
    public String resetPassword(UserDto userDto, Model model) {
        Optional<User> opt = userRepository.findByEmailIgnoreCase(userDto.getEmail());
        if (opt.isPresent()) {
            User user = opt.get();
            user.setPassword(passwordEncoder.encode(userDto.getPassword()));
            userRepository.save(user);
            // TODO: alten Token löschen
            model.addAttribute("success", true);
        } else {
            model.addAttribute("error", true);
        }
        return "reset-password";
    }

    @GetMapping("register")
    public String register(UserDto userDto, Model model) {
        List<String> languageList = Stream.of(Languages.values())
                .map(Enum::name)
                .collect(Collectors.toList());

        model.addAttribute("languages", languageList);

        return "register";
    }

    @PostMapping("register")
    public String registerProcess(@Valid UserDto userDto, BindingResult result, Model model) throws MessagingException {
        List<String> languageList = Stream.of(Languages.values())
                .map(Enum::name)
                .collect(Collectors.toList());

        model.addAttribute("languages", languageList);

        if (!userDto.getPassword().equals(userDto.getPasswordConfirmation())) {
            result.rejectValue("passwordConfirmation", "error.userDto", "Passwörter müssen übereinstimmen.");
            // Ein Fehler für das Objekt im userDto und das Feld passwordConfirmation
        }

        if (userDto.getMotherLanguage().equals(userDto.getTargetLanguage())) {

            result.rejectValue("targetLanguage", "error.userDto", "Beide Sprache können NICHT gleich sein");
        }

        if (result.hasErrors()) {
            return "register";
        }
        try{
            User user = userDto.convert(passwordEncoder);
            userRepository.save(user);
            Token token = new Token(user, Token.TokenType.ACTIVATION);
            tokenRepository.save(token);
            //emailService.sendSimpleEmail(user.getEmail(), "Registrierung", "Du hast dich erfolgreich registriert...");
            //emailService.sendHtmlEmail(user.getEmail(), "Registrierung");
            emailService.sendHtmlRegisterEmail(user, token.getId());
        }catch (Exception e){
            model.addAttribute("usernameCannot", true);
            return "register";
        }

        return "redirect:/register/success";

    }

    @GetMapping("register/success")
    public String registerSuccess(UserDto userDto, Model model) {
        List<String> languageList = Stream.of(Languages.values())
                .map(Enum::name)
                .collect(Collectors.toList());

        model.addAttribute("languages", languageList);

        model.addAttribute("success", true);

        return "register";
    }

    @GetMapping("activate")
    public String checkToken(@RequestParam("token") String tokenStr, Model model) {
        try {
            Optional<Token> opt = tokenRepository.findByIdAndType(UUID.fromString(tokenStr), Token.TokenType.ACTIVATION);

            if (opt.isPresent()) {
                Token token = opt.get();
                User user = token.getUser();
                user.setStatus(UserStatus.ACTIVE);
                userRepository.save(user);
                tokenRepository.delete(token);
                model.addAttribute("success", true);
            } else {
                throw new RuntimeException("Token nicht gefunden.");
            }
        } catch (Exception e) {
            model.addAttribute("error", true);
        }

        return "activate";
    }
}
