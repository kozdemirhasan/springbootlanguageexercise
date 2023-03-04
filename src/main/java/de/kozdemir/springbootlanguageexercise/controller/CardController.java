package de.kozdemir.springbootlanguageexercise.controller;

import de.kozdemir.springbootlanguageexercise.model.Card;
import de.kozdemir.springbootlanguageexercise.model.Word;
import de.kozdemir.springbootlanguageexercise.repository.WordRepository;
import de.kozdemir.springbootlanguageexercise.service.CardService;
import de.kozdemir.springbootlanguageexercise.service.LoginService;
import de.kozdemir.springbootlanguageexercise.service.WordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;


@Controller
@RequestMapping("/card")
public class CardController {

    @Autowired
    LoginService loginService;

    @Autowired
    WordService wordService;

    @Autowired
    WordRepository wordRepository;

    @Autowired
    CardService cardService;


    @GetMapping("add")
    public String add(Long id, Model model) {
        Card card = cardService.addCard(id);


        if (card != null) {
            model.addAttribute("addWord", card.getWord().getWordMeaning());
            model.addAttribute("success", true); // success
        } else {
            model.addAttribute("addWord", wordRepository.findById(id).get().getWordMeaning());
            model.addAttribute("error", true); // success
        }

        //user word list
        List<Card> userCardList = cardService.getUserCardList(loginService.getUser().getId());
        model.addAttribute("userCardList", userCardList);

        return "user-word-list";
    }

    @GetMapping("cards")
    public String userCardList(Model model) {
        model.addAttribute("userCardList", cardService.getUserCardList(loginService.getUser().getId()));
        model.addAttribute("title", "My Word List");
        return "user-word-list";
    }


}
