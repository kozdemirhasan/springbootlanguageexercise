package de.kozdemir.springbootlanguageexercise.controller;

import de.kozdemir.springbootlanguageexercise.model.Languages;
import de.kozdemir.springbootlanguageexercise.model.Word;
import de.kozdemir.springbootlanguageexercise.repository.WordRepository;
import de.kozdemir.springbootlanguageexercise.service.LoginService;
import de.kozdemir.springbootlanguageexercise.service.WordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;


@Controller
@RequestMapping("/word")
public class WordController {

    @Autowired
    LoginService loginService;

    @Autowired
    WordService wordService;
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
        model.addAttribute("motherLanguage", loginService.getUser().getMotherLanguage().name());
        model.addAttribute("targetLanguage", loginService.getUser().getTargetLanguage().name());
        return "new-word";
    }

    @PostMapping("save")
    public String save(@Valid Word word, BindingResult result, Model model) {

        model.addAttribute("motherLanguage", loginService.getUser().getMotherLanguage().name());
        model.addAttribute("targetLanguage", loginService.getUser().getTargetLanguage().name());

        if (result.hasErrors()) {
            return "new-word";
        } else if (wordService.addWord(word) == null) {
            model.addAttribute("error", true); // Dieses Word wurde schon hinzugefügt.
            return "new-word";
        } else {
            model.addAttribute("success", true); // success

            word.setWordMother("");
            word.setWordMeaning("");
            return "new-word";
        }
    }

    @GetMapping("/search")
    public String search(String keyword, Model model) {
        model.addAttribute("keyword", "Search: " + keyword);
        model.addAttribute("words", wordService.wordSearch(keyword));

        return "search-list";
    }
}
