package de.dhbw.web;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by phili on 11.04.2017.
 */
public class JacksonAnswerQuestionBody {
    @JsonProperty
    private long userId;

    public JacksonAnswerQuestionBody() {

    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }
}
