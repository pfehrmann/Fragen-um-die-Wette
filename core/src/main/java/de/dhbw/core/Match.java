package de.dhbw.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Philipp on 30.03.2017.
 */
public class Match implements Identifiable {
    public static final String USERA_ID_COLUMN_NAME = "userAId";
    public static final String USERB_ID_COLUMN_NAME = "userBId";

    private Long id;

    private User userA;

    private User userB;

    private List<Question> questions;

    private int currentQuestionUserA;
    private int currentQuestionUserB;

    private List<Answer> answersUserA;

    private List<Answer> answersUserB;

    private boolean matchFinished;

    public Match(User userA, User userB, List<Question> questions, int currentQuestionUserA, int currentQuestionUserB, List<Answer> answersUserA, List<Answer> answersUserB, boolean matchFinished) {
        this.userA = userA;
        this.userB = userB;
        this.questions = new ArrayList<>();
        if(questions != null) {
            this.questions.addAll(questions);
        }
        this.currentQuestionUserA = currentQuestionUserA;
        this.currentQuestionUserB = currentQuestionUserB;
        this.answersUserA = answersUserA;
        this.answersUserB = answersUserB;
        this.matchFinished = matchFinished;
    }

    public Match() {
    }

    public static Match createNewMatch(User userA, User userB, Collection<Question> questions) {
        List<Question> questionCopy = new ArrayList<>(questions);
        return new Match(userA, userB, questionCopy, 0, 0, new ArrayList<>(), new ArrayList<>(), false);
    }

    @Override
    public Long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getUserA() {
        return userA;
    }

    public void setUserA(User userA) {
        this.userA = userA;
    }

    public void answerQuestion(User user, Answer answer) {
        if(userA == user) {
            answersUserA.add(answer);
            currentQuestionUserA++;
        } else if (userB == user) {
            answersUserB.add(answer);
            currentQuestionUserB++;
        } else {
            throw new RuntimeException("The answering user is not associated withe this match.");
        }
    }

    public User getUserB() {
        return userB;
    }

    public void setUserB(User userB) {
        this.userB = userB;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public int getCurrentQuestionUserA() {
        return currentQuestionUserA;
    }

    public int getCurrentQuestionUserB() {
        return currentQuestionUserB;
    }

    public List<Answer> getAnswersUserA() {
        return answersUserA;
    }

    public List<Answer> getAnswersUserB() {
        return answersUserB;
    }

    public boolean isFinished() {
        return matchFinished;
    }
}
