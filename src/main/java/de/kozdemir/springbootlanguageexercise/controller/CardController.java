package de.kozdemir.springbootlanguageexercise.controller;

import de.kozdemir.springbootlanguageexercise.model.Card;
import de.kozdemir.springbootlanguageexercise.model.LearnStatus;
import de.kozdemir.springbootlanguageexercise.repository.WordRepository;
import de.kozdemir.springbootlanguageexercise.service.CardService;
import de.kozdemir.springbootlanguageexercise.service.LoginService;
import de.kozdemir.springbootlanguageexercise.service.WordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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


    @GetMapping("/add")
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

    @GetMapping("/cards")
    public String userCardList(Model model) {
        List<Card> cardList = cardService.getUserCardList(loginService.getUser().getId());
        if (cardList.size() > 0) {
            model.addAttribute("userCardList", cardList);
            model.addAttribute("title", true);
            return "user-word-list";
        } else {
            model.addAttribute("userCardList", cardList);
            model.addAttribute("emptyWordList", true);
            return "user-word-list";
        }

    }


    // Change Status
    @PostMapping("/status/dontknow")
    public String statusDontknow(Long id, Model model) {

        if (cardService.changeStatusTo(id, LearnStatus.NOTKNOW) != null) {
            model.addAttribute("userCardList", cardService.getUserCardList(loginService.getUser().getId()));
            model.addAttribute("title", "My Word List");

            return "user-word-list";
        }
        model.addAttribute("errorOccurred", true);
        return "user-word-list";
    }

    @PostMapping("/status/little")
    public String statusLittle(Long id, Model model) {

        if (cardService.changeStatusTo(id, LearnStatus.LITTLE) != null) {
            model.addAttribute("userCardList", cardService.getUserCardList(loginService.getUser().getId()));
            model.addAttribute("title", "My Word List");

            return "user-word-list";
        }
        model.addAttribute("errorOccurred", true);
        return "user-word-list";
    }

    @PostMapping("/status/know")
    public String statusKnow(Long id, Model model) {

        if (cardService.changeStatusTo(id, LearnStatus.KNOW) != null) {
            model.addAttribute("userCardList", cardService.getUserCardList(loginService.getUser().getId()));
            model.addAttribute("title", "My Word List");
            return "user-word-list";
        }
        model.addAttribute("errorOccurred", true);
        return "user-word-list";
    }

    //change learn seite status
    @GetMapping("/learn-status/dontknow")
    public void statusLearnDontknow(Long id, Model model) {
        if (cardService.changeStatusTo(id, LearnStatus.NOTKNOW) != null) {
            dontKnowCards(model);
        }
    }

    @GetMapping("/learn-status/little")
    public String statusLearnLittle(Long id, Model model) {
        if (cardService.changeStatusTo(id, LearnStatus.LITTLE) != null) {
            return "redirect:/card/dont-know";
        }
        return null;
    }

    @GetMapping("/learn-status/know")
    public String statusLearnKnow(Long id, Model model) {
        if (cardService.changeStatusTo(id, LearnStatus.KNOW) != null) {
            return "redirect:/card/dont-know";
        }
        return null;
    }

    @GetMapping("/dont-know")
    public String dontKnowCards(Model model) {
        List<Card> lastEightCards = cardService.dontKnowCards();
        model.addAttribute("lastEightCards", lastEightCards);
        model.addAttribute("last8", "Last 8 words");
        return "learning-time";
    }


}
