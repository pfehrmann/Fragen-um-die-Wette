package de.dhbw.web;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import de.dhbw.core.Match;
import de.dhbw.core.User;

/**
 * Created by phili on 10.04.2017.
 */
public class JacksonUser {
    @JsonProperty
    private Long id;

    @JsonProperty
    private String name;

    @JsonProperty
    private List<Long> matchIds;

    public JacksonUser() {
    }

    private JacksonUser(long id, String name, List<Long> matchIds) {
        this.id = id;
        this.name = name;
        this.matchIds = new ArrayList<>(matchIds);
    }

    static JacksonUser createFromUser(User user) {
        List<Long> matchIds = new ArrayList<>();
        for(Match match : user.getMatches()) {
            matchIds.add(match.getId());
        }
        return new JacksonUser(user.getId(), user.getName(), matchIds);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Long> getMatchIds() {
        return matchIds;
    }
}
