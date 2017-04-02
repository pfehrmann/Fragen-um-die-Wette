package de.dhbw.core;

import org.hibernate.search.annotations.Indexed;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Created by Philipp on 30.03.2017.
 */
@Entity
@Indexed
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String text;

    public Answer(String text) {
        this.text = text;
    }

    public Answer() {
    }
}
