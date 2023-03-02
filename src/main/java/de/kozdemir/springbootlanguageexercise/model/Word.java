package de.kozdemir.springbootlanguageexercise.model;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
public class Word implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "WORD_ID")
    private long id;

    @NotEmpty
    private String wordMother;

    @NotEmpty
    private String wordMeaning;

    @Enumerated(EnumType.STRING)
    private Languages motherLanguage;

    @Enumerated(EnumType.STRING)
    private Languages targetLanguage;

    private String level;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime createdAt;

    private long createdUser;

    private String description;

    @OneToMany(mappedBy = "word", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Sentence> sentences;


}
