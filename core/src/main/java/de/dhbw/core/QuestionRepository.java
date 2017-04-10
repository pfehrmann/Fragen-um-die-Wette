package de.dhbw.core;

import java.util.Collection;

/**
 * Created by phili on 06.04.2017.
 */
public interface QuestionRepository {
    Question getQuestionById(long id);
    void persistQuestion(Question question);
    Collection<Question> getRandomQuestions(int count);
}
