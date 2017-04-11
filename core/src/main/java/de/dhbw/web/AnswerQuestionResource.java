package de.dhbw.web;

import org.restlet.ext.jackson.JacksonRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.Post;

import de.dhbw.core.Answer;
import de.dhbw.core.DependecyKnowItAll;
import de.dhbw.core.Match;
import de.dhbw.core.User;

/**
 * Created by phili on 11.04.2017.
 */
public class AnswerQuestionResource extends GenericResource {
    @Post
    public JacksonMatch answerQuestion(Representation entity) {
        long matchId = getIdFromParameter("matchId");
        long answerId = getIdFromParameter("answerId");

        JacksonRepresentation<JacksonAnswerQuestionBody> jacksonAnswerRepresentation = new JacksonRepresentation<>(entity, JacksonAnswerQuestionBody.class);
        JacksonAnswerQuestionBody body = jacksonAnswerRepresentation.getObject();
        long userId = body.getUserId();

        Match match = DependecyKnowItAll.matchRepository.getMatchById(matchId);
        User user = DependecyKnowItAll.userRepository.getUserById(userId);
        Answer answer = DependecyKnowItAll.answerRepository.getAnswerById(answerId);

        match.answerQuestion(user, answer);
        DependecyKnowItAll.matchRepository.persistMatch(match);
        return JacksonMatch.createFromMatch(match);
    }
}
