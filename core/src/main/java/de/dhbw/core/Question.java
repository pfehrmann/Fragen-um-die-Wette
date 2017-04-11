package de.dhbw.core;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Philipp on 30.03.2017.
 */
public class Question implements Identifiable {
    private Long id;

    private String questionText;

    private List<Answer> possibleAnswers;

    private Answer correctAnswer;

    public Question(Long id, String questionText, List<Answer> possibleAnswers, Answer correctAnswer) {
        this.id = id;
        this.questionText = questionText;
        this.possibleAnswers = possibleAnswers;
        this.correctAnswer = correctAnswer;
    }

    public Question() {
    }

    @Override
    public Long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public List<Answer> getPossibleAnswers() {
        return possibleAnswers;
    }

    public void setPossibleAnswers(List<Answer> possibleAnswers) {
        this.possibleAnswers = new ArrayList<>(possibleAnswers);
    }

    public Answer getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(Answer correctAnswer) {
        this.correctAnswer = correctAnswer;
    }
}
