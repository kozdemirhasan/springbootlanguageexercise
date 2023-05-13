package de.kozdemir.springbootlanguageexercise.controller;

import de.kozdemir.springbootlanguageexercise.model.Sentence;
import de.kozdemir.springbootlanguageexercise.model.Word;
import de.kozdemir.springbootlanguageexercise.repository.SentenceRepository;
import de.kozdemir.springbootlanguageexercise.repository.WordRepository;
import de.kozdemir.springbootlanguageexercise.service.LoginService;
import de.kozdemir.springbootlanguageexercise.service.SentenceService;
import de.kozdemir.springbootlanguageexercise.service.WordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;


@Controller
@RequestMapping("/sentence")
public class SentenceController {

    @Autowired
    LoginService loginService;

    @Autowired
    private SentenceService sentenceService;

    @Autowired
    private SentenceRepository sentenceRepository;

    @Autowired
    private WordRepository wordRepository;


    @GetMapping("/new")
    public String add(long id, Sentence sentence, Model model) {
        Word w = wordRepository.findById(id).get();
        model.addAttribute("titleAddSentence", "Add sentence for ( " + w.getWordMeaning() + ": " + w.getWordMother() + " )");
        model.addAttribute("wordId", id);

        return "new-sentence";
    }

    @PostMapping("/add-sentence")
    public String addSentence(long id, String mother, String meaning, Model model) {

        if (mother.isEmpty() || meaning.isEmpty()) {
            model.addAttribute("errorAddSentence", "An error occurred");
            return "redirect:/card/cards";
        } else {

            Word w = wordRepository.findById(id).get();

            Sentence sentence = new Sentence();
            sentence.setWord(w);
            sentence.setSentenceMother(mother);
            sentence.setSentenceMeaning(meaning);
            sentence.setCreatedAt(LocalDateTime.now());
            sentence.setCreatedUser(loginService.getUser().getId());

            sentenceRepository.save(sentence);

            return "redirect:/card/cards";
        }
    }

    @PostMapping("/save")
    public String save(@Valid Sentence sentence, Long wordId, BindingResult result, Model model) {

        sentence.setWord(wordRepository.findById(wordId).get());

        if (result.hasErrors()) {
            return "new-sentence";
        } else if (sentenceService.addSentence(sentence) == null) {
            model.addAttribute("error", true); // Dieses Word wurde schon hinzugefügt.
            return "new-sentence";
        } else {
            model.addAttribute("success", true); // success
            model.addAttribute("title", wordRepository.findById(wordId).get().getWordMother()); // add word
            model.addAttribute("sentencesoftheword", sentenceRepository.findByWordId(wordId)); // sentences for word

            return "word-details";
        }

    }

    @GetMapping("/details")
    public String sentences(Long id, Model model) {
        model.addAttribute("sentencesoftheword", sentenceRepository.findByWordId(id)); // kelime ile ilgili cümleler
        return "word-details";
    }


}
