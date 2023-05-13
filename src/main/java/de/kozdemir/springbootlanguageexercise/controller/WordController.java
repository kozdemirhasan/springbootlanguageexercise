package de.kozdemir.springbootlanguageexercise.controller;

import de.kozdemir.springbootlanguageexercise.model.Word;
import de.kozdemir.springbootlanguageexercise.repository.WordRepository;
import de.kozdemir.springbootlanguageexercise.service.CardService;
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
import java.util.List;


@Controller
@RequestMapping("/word")
public class WordController {

    @Autowired
    LoginService loginService;

    @Autowired
    WordService wordService;

    @Autowired
    WordRepository wordRepository;

    @Autowired
    CardService cardService;

    @GetMapping("/")
    public String userPage(Model model) {
        return "home";
    }

    @GetMapping("new")
    public String add(Word word, Model model) {
        model.addAttribute("motherLanguage", loginService.getUser().getMotherLanguage().name());
        model.addAttribute("targetLanguage", loginService.getUser().getTargetLanguage().name());
        return "new-word";
    }

    @PostMapping("/save")
    public String save(@Valid Word word, BindingResult result, Model model) {

        model.addAttribute("motherLanguage", loginService.getUser().getMotherLanguage().name());
        model.addAttribute("targetLanguage", loginService.getUser().getTargetLanguage().name());

        if (result.hasErrors()) {
            return "new-word";
        }

        Word addedWord = wordService.addWord(word);

        if (addedWord == null) {
            model.addAttribute("error", true); // Dieses Word wurde schon hinzugefügt.
            return "new-word";
        } else {
            model.addAttribute("success", true); // success

            cardService.addCard(addedWord.getId()); // add card for user

            word.setWordMother("");
            word.setWordMeaning("");
            return "new-word";
        }
    }

    @GetMapping("/search")
    public String search(String keyword, Model model) {

        if (keyword != "") {
            model.addAttribute("keyword", "Search: " + keyword);
            model.addAttribute("words", wordService.wordSearch(keyword));

            if (wordService.wordSearch(keyword).size() == 0) {
                model.addAttribute("emp", "No results");
            }

            return "search-list";
        } else {
            model.addAttribute("error", "Please enter the search word..");
            return "search-list";
        }
    }

    @GetMapping("/added")
    public String added(Model model) {
        List<Word> userAddedWords = wordService.userAddedWords();
        model.addAttribute("title", "The words I added.");
        model.addAttribute("userAddedWords", userAddedWords);

        return "user-word-list";
    }

    @GetMapping("/user-words")
    public String userAddedWords(Model model) {

        List<Word> userAddedWords = wordService.userAddedWords();
        model.addAttribute("title", "The words I added.");
        model.addAttribute("userAddedWords", userAddedWords);

        return "user-added-words";
    }

    @GetMapping("/learn")
    public String learn(Model model) {

        return "learning-time";
    }

}
