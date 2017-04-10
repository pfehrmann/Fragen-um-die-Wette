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

        // Matchmaking resources
        router.attach("/match/{me}/{opponent}", MatchResource.class);

        return router;
    }
}
