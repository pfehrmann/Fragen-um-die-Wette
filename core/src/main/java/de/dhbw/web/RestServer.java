package de.dhbw.web;

import org.restlet.Component;
import org.restlet.data.Protocol;
import org.restlet.resource.ServerResource;

import de.dhbw.core.AnswerRepository;

/**
 * Created by Philipp on 06.04.2017.
 */
public class RestServer extends ServerResource {
    public RestServer(AnswerRepository answerRepository) throws Exception {
        // Assign Repositories to the resources
        // TODO make usage of proper dependency injection
        AnswerResource.repository = answerRepository;

        Component component = new Component();
        component.getServers().add(Protocol.HTTP, 8081);
        component.getDefaultHost().attach("/rest", new RestApplication());
        component.start();
    }
}
