package java.de.dhbw.core;

/**
 * Created by phili on 30.03.2017.
 */
public class Question {
    private final String questionText;
    private final Answer[] possibleAnswers = new Answer[4];
    private final Answer correctAnswer;

    public Question(String questionText, Answer correctAnswer) {
        this.questionText = questionText;
        this.correctAnswer = correctAnswer;
    }
}
