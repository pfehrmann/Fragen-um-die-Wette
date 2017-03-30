package java.de.dhbw.core;

import java.util.List;

/**
 * Created by phili on 30.03.2017.
 */
public class User {
    private final String name;
    private final List<Match> matches;

    public User(String name, List<Match> matches) {
        this.name = name;
        this.matches = matches;
    }
}
