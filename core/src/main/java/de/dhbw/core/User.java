package de.dhbw.core;

import org.hibernate.search.annotations.Indexed;

import javax.persistence.*;
import java.util.List;

/**
 * Created by Philipp on 30.03.2017.
 */
@Entity
@Indexed
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    @OneToMany
    private List<Match> matches;

    public User(String name, List<Match> matches) {
        this.name = name;
        this.matches = matches;
    }

    public User() {
    }
}
