package de.kozdemir.springbootlanguageexercise.repository;

import de.kozdemir.springbootlanguageexercise.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardRepository extends JpaRepository<User, Long> {
}