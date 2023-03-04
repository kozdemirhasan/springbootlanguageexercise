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


    @GetMapping("new")
    public String add(long id, Sentence sentence, Model model) {
        Word w = wordRepository.findById(id).get();
        model.addAttribute("titleAddSentence", "Add sentence for ( " + w.getWordMeaning()  +  ": " + w.getWordMother()+ " )");
        model.addAttribute("wordId", id);
//        sentence.setWord(wordRepository.findById(id).get());

//        model.addAttribute("sentence", sentence);

        return "new-sentence";
    }

    @PostMapping("save")
    public String save(@Valid Sentence sentence, Long wordId, BindingResult result, Model model) {

        sentence.setWord(wordRepository.findById(wordId).get());

//        model.addAttribute("motherLanguage", loginService.getUser().getMotherLanguage().name());
//        model.addAttribute("targetLanguage", loginService.getUser().getTargetLanguage().name());

        if (result.hasErrors()) {
            return "new-sentence";
        } else if (sentenceService.addSentence(sentence) == null) {
            model.addAttribute("error", true); // Dieses Word wurde schon hinzugefügt.
            return "new-sentence";
        } else {
            model.addAttribute("success", true); // success

            model.addAttribute("title", wordRepository.findById(wordId).get().getWordMother()); // ekleme yapilan kelime
            model.addAttribute("sentencesoftheword", sentenceRepository.findByWordId(wordId)); // kelime ile ilgili cümleler

            return "word-details";
        }

    }

    @GetMapping("details")
    public String sentences(Long id, Model model){
        model.addAttribute("sentencesoftheword", sentenceRepository.findByWordId(id)); // kelime ile ilgili cümleler
        return "word-details";
    }


}
