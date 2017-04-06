package de.dhbw.core;

import java.util.Collection;
import java.util.List;

/**
 * Repository for answers.
 *
 * Created by Philipp on 06.04.2017.
 */
public interface AnswerRepository {
    /**
     * Get an answer from the database
     * @param id The Id of the answer
     * @return Retusn the answer
     */
    Answer getAnswerById(long id);

    /**
     * Retrieves a list of Answers
     * @param ids The ids to retrieve
     * @return Returns a list of answers from the database
     */
    Collection<Answer> getAnswersById(long ... ids);
    Collection<Answer> getAnswersById(List<Long> ids);

    /**
     * Store an answer in the database
     * @param answer The answer to store
     */
    void persist(Answer answer);
}
