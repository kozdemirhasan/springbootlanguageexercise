package de.kozdemir.springbootlanguageexercise.repository;

import de.kozdemir.springbootlanguageexercise.model.Card;
import de.kozdemir.springbootlanguageexercise.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CardRepository extends JpaRepository<Card, Long> {
    Card findByUserIdAndWordId(Long userId, Long wordId);

    List<Card> findByUserId(long userId);
}
