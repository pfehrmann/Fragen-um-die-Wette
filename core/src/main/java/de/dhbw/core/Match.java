package de.dhbw.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Philipp on 30.03.2017.
 */
public class Match implements Identifiable {
    public static final int QUESTION_COUNT = 18;
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

    public User whoWon() {
        if(!isFinished()) {
            return null;
        }
        int correctAnswersA = 0;
        int correctAnswersB = 0;
        for(int i = 0; i < getQuestions().size(); i++) {
            Answer correctAnswer = this.getQuestions().get(i).getCorrectAnswer();
            long answerUserA = this.getAnswersUserA().get(i).getId();
            long answerUserB = this.getAnswersUserB().get(i).getId();

            if(correctAnswer.getId() == answerUserA) {
                correctAnswersA++;
            }
            if(correctAnswer.getId() == answerUserB) {
                correctAnswersB++;
            }
        }
        if(correctAnswersA > correctAnswersB) {
            return getUserA();
        } else if (correctAnswersA < correctAnswersB) {
            return getUserB();
        }
        return null;
    }

    public User getUserA() {
        return userA;
    }

    public void setUserA(User userA) {
        this.userA = userA;
    }

    public final void answerQuestion(User user, Answer answer) {
        if(isFinished()) {
            return;
        }
        if(getUserA() == user) {
            answerQuestionUserA(answer);
        } else if (getUserB() == user) {
            answerQuestionUserB(answer);
        } else {
            throw new RuntimeException("The answering user is not associated withe this match.");
        }
        updateMatchFinished();
    }

    protected void updateMatchFinished() {
        if (getCurrentQuestionUserA() >= QUESTION_COUNT || getCurrentQuestionUserA() >= getQuestions().size()) {
            if (getCurrentQuestionUserB() >= QUESTION_COUNT || getCurrentQuestionUserB() >= getQuestions().size()) {
                setFinished(true);
                return;
            }
        }
        setFinished(false);
    }

    protected void answerQuestionUserA(Answer answer) {
        answersUserA.add(answer);
        currentQuestionUserA++;
    }

    protected void answerQuestionUserB(Answer answer) {
        answersUserB.add(answer);
        currentQuestionUserB++;
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

    protected void setFinished(boolean finished) {
        this.matchFinished = finished;
    }
}
