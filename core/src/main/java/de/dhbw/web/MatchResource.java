package de.dhbw.web;

import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;

import de.dhbw.application.MatchMakingService;
import de.dhbw.core.Match;
import de.dhbw.core.MatchRepository;
import de.dhbw.core.User;
import de.dhbw.core.UserRepository;

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

    private User getUserFromParameter(String parameterName) {

        Object idObject = getRequest().getAttributes().get(parameterName);
        if(null == idObject) {
            throw new RuntimeException("No id supplied.");
        }
        long id = Long.parseLong(idObject.toString());
        return userRepository.getUserById(id);
    }
}
