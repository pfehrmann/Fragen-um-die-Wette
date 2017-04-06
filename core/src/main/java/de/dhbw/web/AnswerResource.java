package de.dhbw.web;

import org.restlet.ext.jackson.JacksonRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;

import de.dhbw.core.Answer;
import de.dhbw.core.AnswerRepository;

/**
 * Endpoint for Answers
 * Created by phili on 06.04.2017.
 */
public class AnswerResource  extends ServerResource {
    // TODO: Change this to use dependency injection.
    static AnswerRepository repository;

    public AnswerResource() {
    }

    @Get
    public JacksonAnswer getAnswer() {

        Object idObject = getRequest().getAttributes().get("id");
        if(null == idObject) {
            throw new RuntimeException("No id supplied.");
        }
        long id = Long.parseLong(idObject.toString());
        return JacksonAnswer.createFromAnswer(repository.getAnswerById(id));
    }

    @Post
    public long createAnswer(Representation rep) {
        JacksonRepresentation<JacksonAnswer> jacksonAnswerRepresentation = new JacksonRepresentation<>(rep, JacksonAnswer.class);
        JacksonAnswer jacksonAnswer = jacksonAnswerRepresentation.getObject();
        Answer answer = new Answer(jacksonAnswer.getText());
        repository.persist(answer);
        return answer.getId();
    }
}
