package de.dhbw.core;

/**
 * This class represents an answer in the domain.
 *
 * Created by Philipp on 30.03.2017.
 */
public final class Answer implements Identifiable{
    private Long id;

    private String text;

    public Answer() {

    }

    public Answer(Long id, String text) {
        this.id = id;
        this.text = text;
    }

    public String getAnswerText() {
        return text;
    }

    public void setAnswerText(String text) {
        this.text = text;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
