package de.kozdemir.springbootlanguageexercise.service;

import de.kozdemir.springbootlanguageexercise.model.Sentence;
import de.kozdemir.springbootlanguageexercise.model.Word;
import de.kozdemir.springbootlanguageexercise.repository.SentenceRepository;
import de.kozdemir.springbootlanguageexercise.repository.WordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class SentenceService implements Serializable {

    @Autowired
    LoginService loginService;

    @Autowired
    SentenceRepository sentenceRepository;


    public Sentence addSentence(Sentence sentence) {

        sentence.setCreatedAt(LocalDateTime.now());
        sentence.setCreatedUser(loginService.getUser().getId());

        return sentenceRepository.save(sentence);

    }
}
