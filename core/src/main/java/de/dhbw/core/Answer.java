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

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof Answer)) {
            return false;
        }
        Answer answer = (Answer) obj;

        // CHeck for unitialized id. Id is unitialized if not persisted.
        if (answer.id == null || id == null) {
            return false;
        }
        return answer.getId() == getId();
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
