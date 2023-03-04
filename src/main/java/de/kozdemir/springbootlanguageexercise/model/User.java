package de.kozdemir.springbootlanguageexercise.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "APP_USERS") // User Objekt mit der Tabelle app_users aus der DB verbinden
public class User implements Serializable {

    @Id
    @GeneratedValue
    @Column(name = "USER_ID")
    private long id;

    @Column(length = 50, unique = true)
    private String username;

    @Column(length = 50, unique = true)
    private String email;

    @Column(length = 100)
    private String password;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    @Enumerated(EnumType.STRING)
    private UserStatus status;

    @Enumerated(EnumType.STRING)
    private Languages motherLanguage;

    @Enumerated(EnumType.STRING)
    private Languages targetLanguage;

    //Bir user in bircok karti olabilir, bir kartinda bircok user i olabilir

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Card> cards;



}
