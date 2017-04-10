package de.dhbw.core;

import java.util.Collection;

/**
 * Created by phili on 06.04.2017.
 */
public interface MatchRepository {
    Match getMatchById(long id);
    void persistMatch(Match match);
    Collection<Match> getMatchesByUser(User user);

    boolean existsMatch(User userA, User userB);
}
