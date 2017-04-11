package de.dhbw.persistence;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import org.hibernate.search.annotations.Indexed;

import de.dhbw.core.*;

/**
 * Created by phili on 06.04.2017.
 */
public class HibernateUserRepository implements UserRepository{

    public HibernateUserRepository() {
    }

    @Override
    public User getUserById(long id) {
        return DependecyKnowItAll.manager.find(HibernateUser.class, id);
    }

    @Override
    public User getUserByName(String name) {
        return DependecyKnowItAll.manager.createQuery("SELECT u FROM HibernateUser u WHERE u.name = :name", HibernateUser.class)
                .setParameter("name", name).setMaxResults(1).getSingleResult();
    }

    @Override
    public void persistUser(User user) {
        DependecyKnowItAll. manager.getTransaction().begin();
        HibernateUser hibernateUser = HibernateUser.getFromUser(user);
        DependecyKnowItAll.manager.persist(hibernateUser);
        DependecyKnowItAll.manager.getTransaction().commit();
        user.setId(hibernateUser.getId());
    }
}

@Entity
@Indexed
class HibernateUser extends User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true)
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
        List<Match> matches = new ArrayList<>();
        for(String matchId : this.matches.split(";")) {
            long id = Long.parseLong(matchId);
            Match match = DependecyKnowItAll.matchRepository.getMatchById(id);
            matches.add(match);
        }
        return matches;
    }

    public void setMatches(Iterable<Match> matches) {
        StringBuilder sb = new StringBuilder();
        for(Match match : matches) {
            sb.append(match.getId());
            sb.append(";");
        }
        this.matches = sb.toString();
    }

    public void setMatchRepository(MatchRepository matchRepository) {
        DependecyKnowItAll.matchRepository = matchRepository;
    }

    @Override
    public Long getId() {
        return id;
    }
}
