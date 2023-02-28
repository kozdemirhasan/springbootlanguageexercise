package de.kozdemir.springbootlanguageexercise.model;


import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

@Entity@Data
public class Sentence implements Serializable {

    @Id
    @GeneratedValue
    private long id;

    private String sentence;

    private String sentenceMeaning;


}
