package de.kozdemir.springbootlanguageexercise.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Data
public class Word implements Serializable {

    @Id
    @GeneratedValue
    private long id;

//    @Column(length = 100, unique = true)
    @NotEmpty
    private String wordMother;

    @NotEmpty
    private String wordMeaning;

    private String motherLanguage;

    private String targetLanguage;

    private String level;

    private LocalDateTime createdAt;

    private long createdUser;

    private String description;


}
