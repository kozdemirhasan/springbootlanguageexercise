package de.kozdemir.springbootlanguageexercise.controller;

import de.kozdemir.springbootlanguageexercise.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("user")
public class UserController {

    @Autowired
    LoginService loginService;

    @GetMapping("")
    public String userPage(Model model) {
        return "standart";
    }



    @GetMapping("/new")
    public String addword(Model model) {
        model.addAttribute("motherLanguage", loginService.getUser().getMotherLanguage().toString().toLowerCase() );
        model.addAttribute("targetLanguage",  loginService.getUser().getTargetLanguage().toString().toLowerCase());
        return "new-word";
    }

    @PostMapping("/new")
    public String add(String word, String wordMeaning, String sentence, String sentenceMeaning, BindingResult result, Model model) {

        if( word.length()<=0){
            result.rejectValue("word", "error.userDto", "Word is not empty");
        } else if (wordMeaning.length()<=0) {
            result.rejectValue("wordMeaning", "error.userDto", "Word Meaning is not empty");
        }else if (sentence.length()<=0) {
            result.rejectValue("sentence", "error.userDto", "Sentence is not empty");
        }else if (sentenceMeaning.length()<=0) {
            result.rejectValue("sentenceMeaning", "error.userDto", "Sentence Meaning is not empty");

        }


        if(result.hasErrors()) {
            return "register";
        }else{
            System.out.println("kayit yapildi");
        }


        return "new-sentence";
    }

    @GetMapping("/sentence")
    public String addsentences(Model model) {
        return "new-sentence";
    }


}
