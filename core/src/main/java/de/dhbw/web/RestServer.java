package de.dhbw.web;

import org.restlet.Component;
import org.restlet.data.Protocol;
import org.restlet.resource.ServerResource;
import org.restlet.service.CorsService;

import de.dhbw.core.AnswerRepository;
import de.dhbw.core.MatchRepository;
import de.dhbw.core.UserRepository;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;

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

        CorsService corsService = new CorsService();
        corsService.setAllowedOrigins(new HashSet(Collections.singletonList("*")));
        corsService.setAllowedCredentials(true);

        Component component = new Component();
        component.getServices().add(corsService);
        component.getServers().add(Protocol.HTTP, 8081);
        component.getDefaultHost().attach("/rest", new RestApplication());
        component.start();
    }
}
