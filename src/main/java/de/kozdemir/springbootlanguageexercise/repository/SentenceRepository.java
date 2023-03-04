package de.kozdemir.springbootlanguageexercise.repository;


import de.kozdemir.springbootlanguageexercise.model.Sentence;
import de.kozdemir.springbootlanguageexercise.model.Word;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SentenceRepository extends JpaRepository<Sentence, Long> {


    List<Sentence> findByWordId(Long wordId);
}
