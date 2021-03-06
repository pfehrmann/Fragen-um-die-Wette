package de.dhbw.web;

import org.restlet.Application;
import org.restlet.Restlet;
import org.restlet.routing.Router;

/**
 * Created by phili on 06.04.2017.
 */
public class RestApplication extends Application {
    @Override
    public Restlet createInboundRoot() {
        Router router = new Router(getContext());

        // Answer resources
        router.attach("/answer/{id}", AnswerResource.class);
        router.attach("/answer/", AnswerResource.class);

        // Match resources
        router.attach("/match/{me}/{opponent}", MatchResource.class);
        router.attach("/match/{id}", MatchResource.class);

        // Answer Question resource
        router.attach("/answerQuestion/{matchId}/answer/{answerId}", AnswerQuestionResource.class);

        // User resource
        router.attach("/user/{id}", UserResource.class);

        // Question resources
        router.attach("/question/{id}", QuestionResource.class);

        return router;
    }
}
