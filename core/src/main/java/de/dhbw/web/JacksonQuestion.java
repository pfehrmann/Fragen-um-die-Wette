package de.dhbw.web;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import de.dhbw.core.Answer;
import de.dhbw.core.Question;

/**
 * Created by phili on 10.04.2017.
 */
public class JacksonQuestion {
    @JsonProperty
    private long id;

    @JsonProperty
    private String questionText;

    @JsonProperty
    private List<JacksonAnswer> possibleAnswers;

    @JsonProperty
    private JacksonAnswer correctAnswer;

    public JacksonQuestion() {

    }

    public static JacksonQuestion createFromQuestion(Question question) {
        JacksonQuestion jacksonQuestion = new JacksonQuestion();
        jacksonQuestion.id = question.getId();
        jacksonQuestion.questionText = question.getQuestionText();
        jacksonQuestion.possibleAnswers = new ArrayList<>();
        for(Answer answer : question.getPossibleAnswers()) {
            jacksonQuestion.possibleAnswers.add(JacksonAnswer.createFromAnswer(answer));
        }
        jacksonQuestion.correctAnswer = JacksonAnswer.createFromAnswer(question.getCorrectAnswer());
        jacksonQuestion.questionText = question.getQuestionText();
        return jacksonQuestion;
    }
}
