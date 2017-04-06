package de.dhbw.core;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.search.annotations.Indexed;

/**
 * This class represents an answer in the domain.
 * 
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

    public String getAnswerText() {
        return text;
    }

    public long getId() {
        return id;
    }
}
