package java.de.dhbw.core;

import java.util.List;

/**
 * Created by phili on 30.03.2017.
 */
public class Match {
    private final User userA;
    private final User userB;

    private final List<Question> questions;

    private final int currentQuestionUserA;
    private final int currentQuestionUserB;

    private final List<Answer> answersUserA;
    private final List<Answer> answersUserB;

    private final boolean matchFinished;

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
}
