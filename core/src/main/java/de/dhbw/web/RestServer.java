package de.dhbw.web;

import org.restlet.Component;
import org.restlet.data.Protocol;
import org.restlet.resource.ServerResource;

import de.dhbw.core.AnswerRepository;
import de.dhbw.core.MatchRepository;
import de.dhbw.core.UserRepository;

/**
 * Created by Philipp on 06.04.2017.
 */
public class RestServer extends ServerResource {
    public RestServer(AnswerRepository answerRepository, MatchRepository matchRepository, UserRepository userRepository) throws Exception {
        // Assign Repositories to the resources
        // TODO make usage of proper dependency injection
        AnswerResource.repository = answerRepository;
        MatchResource.matchRepository = matchRepository;
        MatchResource.userRepository = userRepository;

        Component component = new Component();
        component.getServers().add(Protocol.HTTP, 8081);
        component.getDefaultHost().attach("/rest", new RestApplication());
        component.start();
    }
}
