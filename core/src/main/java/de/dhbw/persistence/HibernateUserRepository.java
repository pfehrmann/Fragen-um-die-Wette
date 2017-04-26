package de.dhbw.persistence;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.*;

import org.hibernate.search.annotations.*;
import org.hibernate.search.annotations.Index;

import de.dhbw.core.*;

/**
 * Created by phili on 06.04.2017.
 */
public class HibernateUserRepository implements UserRepository{

    public HibernateUserRepository() {
    }

    @Override
    public User getUserById(long id) {
        HibernateUser user = DependecyKnowItAll.manager.find(HibernateUser.class, id);
        DependecyKnowItAll.manager.refresh(user);
        return user;
    }

    @Override
    public User getUserByName(String name) {
        String sqlQuery = "select * from \"HibernateUser\" WHERE \"name\" = ? ALLOW FILTERING";
        Query nativeQuery = DependecyKnowItAll.manager.createNativeQuery(sqlQuery, HibernateUser.class);
        nativeQuery = nativeQuery.setParameter(1, name);
        nativeQuery = nativeQuery.setMaxResults(1);
        try {
            // This will result in a NoResultException if not Result is found, thus the try catch
            HibernateUser user = (HibernateUser) nativeQuery.getSingleResult();
            DependecyKnowItAll.manager.refresh(user);
            return user;
        } catch(NoResultException ex) {
            // No action is needed here.
            // TODO use some proper exception handling
        }
        throw new RuntimeException("User " + name + " not found.");
    }

    @Override
    public void persistUser(User user) {
        DependecyKnowItAll.manager.getTransaction().begin();
        HibernateUser hibernateUser = HibernateUser.getFromUser(user);
        DependecyKnowItAll.manager.persist(hibernateUser);
        DependecyKnowItAll.manager.getTransaction().commit();
        user.setId(hibernateUser.getId());
    }
}

@Entity
@Indexed
@Cacheable(false)
class HibernateUser extends User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true)
    @Field(index=Index.YES, analyze= Analyze.NO, store= Store.YES)
    private String name;

    private String matches;

    public HibernateUser(String name, List<Match> matches) {
        this.name = name;
       this.setMatches(matches);
    }

    public HibernateUser() {
    }

    public HibernateUser(String name) {
        this.name = name;
    }

    public static HibernateUser getFromUser(User user) {
        HibernateUser hibernateUser = new HibernateUser(user.getName(), user.getMatches());
        return hibernateUser;
    }

    @Override
    public List<Match> getMatches() {
        Collection<Match> matches = DependecyKnowItAll.matchRepository.getMatchesByUser(this);
        return new ArrayList<>(matches);
    }

    @Override
    public void setMatches(Collection<Match> matches) {
        this.matches = "";
    }

    public void setMatchRepository(MatchRepository matchRepository) {
        DependecyKnowItAll.matchRepository = matchRepository;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Long getId() {
        return id;
    }
}
