package de.kozdemir.springbootlanguageexercise.model;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
public class Artikel implements Serializable {

    @Id
    @GeneratedValue
    private long id;
}
