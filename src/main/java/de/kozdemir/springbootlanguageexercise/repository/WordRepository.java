package de.kozdemir.springbootlanguageexercise.repository;

import de.kozdemir.springbootlanguageexercise.model.Languages;
import de.kozdemir.springbootlanguageexercise.model.Word;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WordRepository extends JpaRepository<Word, Long> {
    Word findByWordMotherAndMotherLanguage(String wordMother, Languages motherLanguage);

    List<Word> findByWordMotherContainingIgnoreCaseOrWordMeaningContainingIgnoreCaseAndMotherLanguageAndTargetLanguage(String wordMother, String wordMeaning,
                                                                                                                       Languages motherLanguage, Languages targetLanguage);
}
