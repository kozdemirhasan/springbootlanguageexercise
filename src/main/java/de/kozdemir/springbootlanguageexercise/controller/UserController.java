package de.kozdemir.springbootlanguageexercise.controller;

import de.kozdemir.springbootlanguageexercise.model.Word;
import de.kozdemir.springbootlanguageexercise.repository.WordRepository;
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
import java.time.LocalDateTime;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    LoginService loginService;

    @Autowired
    WordRepository wordRepository;

    @GetMapping("")
    public String userPage(Model model) {
        return "home";
    }

//    @GetMapping("words")
//    public String sentences(Model model) {
//        return "user-words";
//    }


    @GetMapping("new")
    public String add(Word word, Model model) {
        model.addAttribute("motherLanguage", loginService.getUser().getMotherLanguage().toString().toLowerCase());
        model.addAttribute("targetLanguage", loginService.getUser().getTargetLanguage().toString().toLowerCase());
        return "new-word";
    }

    @PostMapping("save")
    public String save(@Valid Word word, BindingResult result, Model model)  {

        if (result.hasErrors()) {
            return "new-word";
        } else {
            word.setMotherLanguage(loginService.getUser().getMotherLanguage().toString());
            word.setTargetLanguage(loginService.getUser().getTargetLanguage().toString());
            word.setCreatedAt(LocalDateTime.now());
            word.setLevel(word.getLevel());
            word.setCreatedUser(loginService.getUser().getId());

            wordRepository.save(word);

            model.addAttribute("success", true);

            word.setWordMother("");
            word.setWordMeaning("");

            return "new-word";
        }
    }

    @GetMapping("new/success")
    public String registerSuccess(Word word, Model model) {
        model.addAttribute("success", true);
        return "new-word";
    }


}
