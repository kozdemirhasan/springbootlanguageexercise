package de.kozdemir.springbootlanguageexercise.service;

import de.kozdemir.springbootlanguageexercise.model.Card;
import de.kozdemir.springbootlanguageexercise.model.Languages;
import de.kozdemir.springbootlanguageexercise.model.Word;
import de.kozdemir.springbootlanguageexercise.repository.CardRepository;
import de.kozdemir.springbootlanguageexercise.repository.WordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class WordService implements Serializable {

    @Autowired
    LoginService loginService;

    @Autowired
    WordRepository wordRepository;

    @Autowired
    CardRepository cardRepository;

    @Autowired
    CardService cardService;

    public Word addWord(Word word) {
        Word w = wordControll(word);
        if (w == null) {
            word.setMotherLanguage(loginService.getUser().getMotherLanguage());
            word.setTargetLanguage(loginService.getUser().getTargetLanguage());
            word.setCreatedAt(LocalDateTime.now());
            word.setLevel(word.getLevel());
            word.setCreatedUser(loginService.getUser().getId());

           return wordRepository.save(word); // add new word
        }
        return null;
    }

    public Word wordControll(Word word) {
        return wordRepository.findByWordMotherAndMotherLanguage(word.getWordMother(), loginService.getUser().getMotherLanguage());
    }

    public List<Word> wordSearch(String keyword) {

        return wordRepository.findByWordMotherContainingIgnoreCaseOrWordMeaningContainingIgnoreCaseAndMotherLanguageAndTargetLanguage(
                keyword, keyword, loginService.getUser().getMotherLanguage(), loginService.getUser().getTargetLanguage());
    }

    public List<Word> userAddedWords() {
        return wordRepository.findByCreatedUserOrderByCreatedAtDesc(loginService.getUser().getId());
    }
}
