package de.kozdemir.springbootlanguageexercise.model;


import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Data
public class Word implements Serializable {

    @Id
    @GeneratedValue
    private long id;

    private String word;

    private String wordMeaning;

    private String mLang;

    private String tLang;

    private String level;

    private LocalDateTime createdAt;

    private long createdUser;

    private String description;


}
