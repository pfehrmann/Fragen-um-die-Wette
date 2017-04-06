package de.dhbw.web;

import org.codehaus.jackson.annotate.JsonProperty;

import de.dhbw.core.Answer;

/**
 * Jackson representation of an {@link Answer}
 * Created by phili on 06.04.2017.
 */
public class JacksonAnswer {
    @JsonProperty
    private String text;

    public JacksonAnswer() {

    }

    private JacksonAnswer(String text) {
        this.text = text;
    }

    static JacksonAnswer createFromAnswer(Answer answer) {
        return new JacksonAnswer(answer.getAnswerText());
    }

    String getText() {
        return text;
    }
}
