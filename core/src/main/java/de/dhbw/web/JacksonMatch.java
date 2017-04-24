package de.dhbw.web;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import de.dhbw.core.Answer;
import de.dhbw.core.Match;
import de.dhbw.core.Question;
import de.dhbw.core.User;

/**
 * Created by phili on 10.04.2017.
 */
public class JacksonMatch {
    @JsonProperty
    private long id;

    @JsonProperty
    private long userA;

    @JsonProperty
    private long userB;

    @JsonProperty
    private List<Long> questionsIds;

    @JsonProperty
    private int currentQuestionUserA;

    @JsonProperty
    private int currentQuestionUserB;

    @JsonProperty
    private List<JacksonAnswer> answersUserA;

    @JsonProperty
    private List<JacksonAnswer> answersUserB;

    @JsonProperty
    private boolean matchFinished;

    @JsonProperty
    private JacksonUser whoWon;

    public JacksonMatch() {

    }

    public static JacksonMatch createFromMatch(Match match) {
        if(match == null) {
            throw new NullPointerException("Match was null.");
        }
        JacksonMatch jacksonMatch = new JacksonMatch();
        jacksonMatch.id = match.getId();
        jacksonMatch.userA = match.getUserA().getId();
        jacksonMatch.userB = match.getUserB().getId();

        jacksonMatch.questionsIds = new ArrayList<>();
        for(Question question : match.getQuestions()) {
            jacksonMatch.questionsIds.add(question.getId());
        }

        jacksonMatch.currentQuestionUserA = match.getCurrentQuestionUserA();
        jacksonMatch.currentQuestionUserB = match.getCurrentQuestionUserB();

        jacksonMatch.answersUserA = new ArrayList<>();
        for(Answer answer : match.getAnswersUserA()) {
            jacksonMatch.answersUserA.add(JacksonAnswer.createFromAnswer(answer));
        }

        jacksonMatch.answersUserB = new ArrayList<>();
        for(Answer answer : match.getAnswersUserB()) {
            jacksonMatch.answersUserB.add(JacksonAnswer.createFromAnswer(answer));
        }

        jacksonMatch.matchFinished = match.isFinished();

        User won = match.whoWon();
        if(won != null) {
            jacksonMatch.whoWon = JacksonUser.createFromUser(match.whoWon());
        } else {
            jacksonMatch.whoWon = null;
        }

        return jacksonMatch;
    }
}
