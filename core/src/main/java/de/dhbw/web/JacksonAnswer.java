package de.dhbw.web;

import com.fasterxml.jackson.annotation.JsonProperty;

import de.dhbw.core.Answer;

/**
 * Jackson representation of an {@link Answer}
 * Created by phili on 06.04.2017.
 */
public class JacksonAnswer {
    @JsonProperty
    private long id;

    @JsonProperty
    private String text;

    public JacksonAnswer() {

    }

    private JacksonAnswer(long id, String text) {
        this.id = id;
        this.text = text;
    }

    static JacksonAnswer createFromAnswer(Answer answer) {
        return new JacksonAnswer(answer.getId(), answer.getAnswerText());
    }

    public String getText() {
        return text;
    }

    public long getId() {
        return id;
    }
}
