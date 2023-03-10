package de.kozdemir.springbootlanguageexercise.model;


import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "SENTENCES")
public class Sentence implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SENTENCE_ID")
    private long id;

    @NotEmpty
    private String sentenceMother;

    @NotEmpty
    private String sentenceMeaning;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime createdAt;

    private long createdUser;

    private String description;

    @JoinColumn(name = "WORD_ID", referencedColumnName = "WORD_ID")
    @ManyToOne
    private Word word;


}
