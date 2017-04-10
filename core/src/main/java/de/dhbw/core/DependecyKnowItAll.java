package de.dhbw.core;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Created by phili on 10.04.2017.
 */
public class DependecyKnowItAll {
    public static UserRepository userRepository;
    public static MatchRepository matchRepository;
    public static QuestionRepository questionRepository;
    public static AnswerRepository answerRepository;
    public static EntityManager manager;

    static {
        manager = create();
    }

    public static EntityManager create() {
        if(manager == null) {
            EntityManagerFactory factory = Persistence.createEntityManagerFactory("cassandra");
            manager = factory.createEntityManager();
        }
        return manager;
    }
}
