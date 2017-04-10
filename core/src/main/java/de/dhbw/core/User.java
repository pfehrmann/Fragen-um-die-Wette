package de.dhbw.core;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Philipp on 30.03.2017.
 */
public class User implements Identifiable {
    private Long id;

    private String name;

    private List<Match> matches;

    public User(String name) {
        initialize();
        this.name = name;
    }

    public User(String name, List<Match> matches) {
        initialize();
        this.name = name;
        if(matches != null) {
            this.matches.addAll(matches);
        }
    }

    public User() {
        initialize();
    }

    private void initialize() {
        this.matches = new ArrayList<>();
    }

    @Override
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<Match> getMatches() {
        return new ArrayList<>(this.matches);
    }

    public String getName() {
        return name;
    }
}
