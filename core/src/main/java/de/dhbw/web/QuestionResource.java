package de.dhbw.web;

import org.restlet.resource.Get;

import de.dhbw.core.DependecyKnowItAll;
import de.dhbw.core.Question;

/**
 * Created by phili on 10.04.2017.
 */
public class QuestionResource extends GenericResource {
    @Get
    public JacksonQuestion getQuestion() {
        long id = getIdFromParameter("id");
        Question question = DependecyKnowItAll.questionRepository.getQuestionById(id);
        return JacksonQuestion.createFromQuestion(question);
    }
}
