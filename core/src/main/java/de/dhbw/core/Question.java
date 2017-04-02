package de.dhbw.core;

import org.hibernate.search.annotations.Indexed;

import javax.persistence.*;
import java.util.List;

/**
 * Created by Philipp on 30.03.2017.
 */
@Entity
@Indexed
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String questionText;

    @OneToMany
    private List<Answer> possibleAnswers;

    @OneToOne
    private Answer correctAnswer;

    public Question(Long id, String questionText, List<Answer> possibleAnswers, Answer correctAnswer) {
        this.id = id;
        this.questionText = questionText;
        this.possibleAnswers = possibleAnswers;
        this.correctAnswer = correctAnswer;
    }

    public Question() {
    }
}
