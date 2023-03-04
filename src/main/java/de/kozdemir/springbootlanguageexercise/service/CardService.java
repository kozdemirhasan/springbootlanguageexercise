package de.kozdemir.springbootlanguageexercise.service;

import de.kozdemir.springbootlanguageexercise.model.Card;
import de.kozdemir.springbootlanguageexercise.model.LearnStatus;
import de.kozdemir.springbootlanguageexercise.model.User;
import de.kozdemir.springbootlanguageexercise.model.Word;
import de.kozdemir.springbootlanguageexercise.repository.CardRepository;
import de.kozdemir.springbootlanguageexercise.repository.UserRepository;
import de.kozdemir.springbootlanguageexercise.repository.WordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class CardService implements Serializable {

    @Autowired
    LoginService loginService;

    @Autowired
    WordRepository wordRepository;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CardRepository cardRepository;


    public Card addCard(Long id) {

        User user = loginService.getUser();
        Word word = wordRepository.findById(id).get();

        Card card = new Card();
        card.setUser(user);
        card.setWord(word);
        card.setCreatedAt(LocalDateTime.now());
        card.setLastTime(LocalDateTime.now());
        card.setLearnStatus(LearnStatus.DONTKNOW);

        if(cardControll(card)){
            return cardRepository.save(card);
        }
        return null;
    }

    public boolean cardControll(Card card) {
        if (cardRepository.findByUserIdAndWordId(card.getUser().getId(), card.getWord().getId()) == null)
            return true;
        else
            return false;
    }


    public List<Card> getUserCardList(long id) {
       List<Card> userCards =  cardRepository.findByUserId(id);
       return  userCards;
    }
}
