package de.dhbw.core;

import org.hibernate.search.annotations.Indexed;

import javax.persistence.*;
import java.util.List;

/**
 * Created by Philipp on 30.03.2017.
 */
@Entity
@Indexed
public class Match {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private User userA;

    @ManyToOne
    private User userB;

    @OneToMany
    private List<Question> questions;

    private int currentQuestionUserA;
    private int currentQuestionUserB;

    @OneToMany
    private List<Answer> answersUserA;

    @OneToMany
    private List<Answer> answersUserB;

    private boolean matchFinished;

    public Match(User userA, User userB, List<Question> questions, int currentQuestionUserA, int currentQuestionUserB, List<Answer> answersUserA, List<Answer> answersUserB, boolean matchFinished) {
        this.userA = userA;
        this.userB = userB;
        this.questions = questions;
        this.currentQuestionUserA = currentQuestionUserA;
        this.currentQuestionUserB = currentQuestionUserB;
        this.answersUserA = answersUserA;
        this.answersUserB = answersUserB;
        this.matchFinished = matchFinished;
    }

    public Match() {
    }
}
