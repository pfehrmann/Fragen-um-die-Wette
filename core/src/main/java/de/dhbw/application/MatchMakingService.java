package de.dhbw.application;

import java.util.Collection;

import com.sun.istack.internal.NotNull;

import de.dhbw.core.DependecyKnowItAll;
import de.dhbw.core.Match;
import de.dhbw.core.Question;
import de.dhbw.core.User;

/**
 * Created by phili on 06.04.2017.
 */
public class MatchMakingService {

    public Match createMatch(@NotNull User userA, @NotNull User userB) {
        ensureNoRunningMatch(userA, userB);
        Collection<Question> questions = DependecyKnowItAll.questionRepository.getRandomQuestions(Match.QUESTION_COUNT);
        Match match = Match.createNewMatch(userA, userB, questions);
        return match;
    }

    private void ensureNoRunningMatch(User userA, User userB) {
        if(DependecyKnowItAll.matchRepository.existsMatch(userA, userB)) {
            throw new RuntimeException("Match already exists!");
        }
    }
}
