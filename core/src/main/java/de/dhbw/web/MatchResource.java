package de.dhbw.web;

import org.restlet.resource.Get;
import org.restlet.resource.Post;

import de.dhbw.application.MatchMakingService;
import de.dhbw.core.*;

/**
 * Created by phili on 06.04.2017.
 */
public class MatchResource extends GenericResource {
    // TODO use some proper injection...
    public static MatchRepository matchRepository;
    public static UserRepository userRepository;

    @Post
    public JacksonMatch createMatch() {
        User userA = getUserFromParameter("me");
        User userB = getUserFromParameter("opponent");

        MatchMakingService matchMakingService = new MatchMakingService();
        Match match;
        try {
            match = matchMakingService.createMatch(userA, userB);
            matchRepository.persistMatch(match);
        } catch (Exception e) {
            match = DependecyKnowItAll.matchRepository.getMatch(userA, userB);
        }
        return JacksonMatch.createFromMatch(match);
    }

    @Get
    public JacksonMatch getMatch() {
        long id = getIdFromParameter("id");
        Match match = DependecyKnowItAll.matchRepository.getMatchById(id);
        return JacksonMatch.createFromMatch(match);
    }

    private User getUserFromParameter(String parameterName) {
        Object idObject = getRequest().getAttributes().get(parameterName);
        if(null == idObject) {
            throw new RuntimeException("Parameter id or name is not supplied or null.");
        }
        try {
            long id = Long.parseLong(idObject.toString());
            return DependecyKnowItAll.userRepository.getUserById(id);
        } catch (Exception e) {
            System.out.println("No user found by id");
        }
        return DependecyKnowItAll.userRepository.getUserByName((String) idObject);
    }
}
