package de.dhbw.web;

import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;

import de.dhbw.application.MatchMakingService;
import de.dhbw.core.*;

/**
 * Created by phili on 06.04.2017.
 */
public class MatchResource extends ServerResource {
    // TODO use some proper injection...
    public static MatchRepository matchRepository;
    public static UserRepository userRepository;

    @Post
    public long createMatch() {
        User userA = getUserFromParameter("me");
        User userB = getUserFromParameter("opponent");

        MatchMakingService matchMakingService = new MatchMakingService();

        Match match = matchMakingService.createMatch(userA, userB);
        matchRepository.persistMatch(match);
        return match.getId();
    }

    @Get
    public JacksonMatch getMatch() {
        long id = getIdFromParameter("id");
        Match match = DependecyKnowItAll.matchRepository.getMatchById(id);
        return JacksonMatch.createFromMatch(match);
    }

    private long getIdFromParameter(String parameterName) {
        Object idObject = getRequest().getAttributes().get(parameterName);
        if(null == idObject) {
            throw new RuntimeException("Parameter " + parameterName + " is not supplied or null.");
        }
        return Long.parseLong(idObject.toString());
    }

    private User getUserFromParameter(String parameterName) {
        long id = getIdFromParameter(parameterName);
        return userRepository.getUserById(id);
    }
}
