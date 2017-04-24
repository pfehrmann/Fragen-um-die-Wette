package de.dhbw.persistence;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.*;

import org.hibernate.search.annotations.Indexed;

import de.dhbw.core.*;

/**
 * Created by phili on 06.04.2017.
 */
public class HibernateMatchRepository implements MatchRepository {
    public HibernateMatchRepository() {

    }

    @Override
    public Match getMatchById(long id) {
        return DependecyKnowItAll.manager.find(HibernateMatch.class, id);
    }

    @Override
    public void persistMatch(Match match) {
        HibernateMatch hibernateMatch;
        DependecyKnowItAll.manager.getTransaction().begin();
        try {
            hibernateMatch = HibernateMatch.from(match);

            // Check if entity is detached
            if(isDetached(hibernateMatch)) {
                DependecyKnowItAll.manager.merge(match);
            } else {
                DependecyKnowItAll.manager.persist(hibernateMatch);
            }
            DependecyKnowItAll.manager.getTransaction().commit();
        } catch (Exception e) {
            throw e;
        } finally {
            if(DependecyKnowItAll.manager.getTransaction().isActive()) {
                DependecyKnowItAll.manager.getTransaction().rollback();
            }
        }
        match.setId(hibernateMatch.getId());
    }

    private boolean isDetached(HibernateMatch match) {
        return match.getId() != null  // must not be transient
                && !DependecyKnowItAll.manager.contains(match)  // must not be managed now
                && DependecyKnowItAll.manager.find(HibernateMatch.class, match.getId()) != null;  // must not have been removed
    }

    @Override
    public Collection<Match> getMatchesByUser(User user) {
        String query = "select * " +
                "from \"HibernateMatch\" " +
                "where \"" + Match.USERA_ID_COLUMN_NAME + "\" = ? " +
                "ALLOW FILTERING";


        String query2 = "select * " +
                "from \"HibernateMatch\" " +
                "where \"" + Match.USERB_ID_COLUMN_NAME + "\" = ? " +
                "ALLOW FILTERING";

        Query nativeQuery = DependecyKnowItAll.manager.createNativeQuery(query, HibernateMatch.class);
        nativeQuery = nativeQuery.setParameter(1, user.getId());

        List<Match> matches = new ArrayList<>();
        for(Object result : nativeQuery.getResultList()) {
            matches.add((HibernateMatch) result);
        }

        nativeQuery = DependecyKnowItAll.manager.createNativeQuery(query2, HibernateMatch.class);
        nativeQuery = nativeQuery.setParameter(1, user.getId());

        for(Object result : nativeQuery.getResultList()) {
            matches.add((HibernateMatch) result);
        }

        return matches;
    }

    @Override
    public boolean existsMatch(User userA, User userB) {
        return getMatch(userA, userB) != null;
    }
    @Override
    public Match getMatch(User userA, User userB) {
        // Using named parameters would be more awesome, but is not supported yet.
        // See https://hibernate.atlassian.net/browse/OGM-1008
        String query = "select * " +
                "from \"HibernateMatch\" " +
                "where \"" + Match.USERA_ID_COLUMN_NAME + "\" = ? " +
                "and \"" + Match.USERB_ID_COLUMN_NAME + "\" = ? " +
                "and \"matchFinished\" = false " +
                "ALLOW FILTERING";


        String query2 = "select * " +
                "from \"HibernateMatch\" " +
                "where \"" + Match.USERB_ID_COLUMN_NAME + "\" = ? " +
                "and \"" + Match.USERA_ID_COLUMN_NAME + "\" = ? " +
                "and \"matchFinished\" = false " +
                "ALLOW FILTERING";

        Query nativeQuery = DependecyKnowItAll.manager.createNativeQuery(query, HibernateMatch.class);
        nativeQuery = nativeQuery.setParameter(1, userA.getId());
        nativeQuery = nativeQuery.setParameter(2, userB.getId());
        nativeQuery = nativeQuery.setMaxResults(1);

        try {
            // This will result in a NoResultException if not Result is found, thus the try catch
            Object queryResultObject = nativeQuery.getSingleResult();
            if (queryResultObject != null) {
                return (HibernateMatch) queryResultObject;
            }
        } catch(NoResultException ex) {
            // No action is needed here.
            // TODO use some proper exception handling
        }

        try {
            Object queryResultObject = DependecyKnowItAll.manager.createNativeQuery(query2, HibernateMatch.class)
                    .setParameter(1, userA.getId())
                    .setParameter(2, userB.getId())
                    .setMaxResults(1)
                    .getSingleResult();
            if (queryResultObject != null) {
                return (HibernateMatch) queryResultObject;
            }
        } catch(NoResultException ex) {
            // No action is needed here.
        }
        return null;
    }
}

@Entity
@Indexed
class HibernateMatch extends Match{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = USERA_ID_COLUMN_NAME)
    private long userAId;

    @Column(name = USERB_ID_COLUMN_NAME)
    private long userBId;

    private String questions;

    private int currentQuestionUserA;
    private int currentQuestionUserB;

    private String answersUserA;
    private String answersUserB;

    private boolean matchFinished;

    public HibernateMatch(long id, User userA, User userB, List<Question> questions, int currentQuestionUserA, int currentQuestionUserB, List<Answer> answersUserA, List<Answer> answersUserB, boolean matchFinished) {
        this.id = id;
        this.setUserA(userA);
        this.setUserB(userB);
        this.setQuestions(questions);
        this.currentQuestionUserA = currentQuestionUserA;
        this.currentQuestionUserB = currentQuestionUserB;
        this.setAnswersUserA(answersUserA);
        this.setAnswersUserB(answersUserB);
        this.matchFinished = matchFinished;
    }

    public HibernateMatch() {
        this.questions = "";
    }

    public static HibernateMatch from(Match match) {
        HibernateMatch hibernateMatch;
        try {
            hibernateMatch = new HibernateMatch(match.getId(), match.getUserA(), match.getUserB(), match.getQuestions(), match.getCurrentQuestionUserA(), match.getCurrentQuestionUserB(), match.getAnswersUserA(), match.getAnswersUserB(), match.isFinished());
        } catch (NullPointerException e) {
            hibernateMatch = new HibernateMatch();
            hibernateMatch.setUserA(match.getUserA());
            hibernateMatch.setUserB(match.getUserB());
            hibernateMatch.setQuestions(match.getQuestions());
            hibernateMatch.currentQuestionUserA = match.getCurrentQuestionUserA();
            hibernateMatch.currentQuestionUserB = match.getCurrentQuestionUserB();
            hibernateMatch.setAnswersUserA(match.getAnswersUserA());
            hibernateMatch.setAnswersUserB(match.getAnswersUserB());
            hibernateMatch.matchFinished = match.isFinished();
        }
        return hibernateMatch;
    }

    @Override
    protected void answerQuestionUserA(Answer answer) {
        List<Answer> answersUserA = getAnswersUserA();
        answersUserA.add(answer);
        setAnswersUserA(answersUserA);
        currentQuestionUserA++;
    }

    @Override
    protected void answerQuestionUserB(Answer answer) {
        List<Answer> answersUserB = getAnswersUserB();
        answersUserB.add(answer);
        setAnswersUserB(answersUserB);
        currentQuestionUserB++;
    }

    @Override
    public User getUserA() {
        return DependecyKnowItAll.userRepository.getUserById(userAId);
    }

    @Override
    public void setUserA(User user) {
        this.userAId = user.getId();
    }

    @Override
    public User getUserB() {
        return DependecyKnowItAll.userRepository.getUserById(userBId);
    }

    @Override
    public void setUserB(User user) {
        this.userBId = user.getId();
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public List<Answer> getAnswersUserA() {
        return  getAnswersFromString(this.answersUserA);
    }

    public void setAnswersUserA(List<Answer> answers) {
        this.answersUserA = getStringFromAnswers(answers);
    }

    private List<Answer> getAnswersFromString(String s) {
        List<Answer> answers = new ArrayList<>();
        if(s.isEmpty()) {
            return answers;
        }
        for(String answerId : s.split(";")) {
            long id = Long.parseLong(answerId);
            Answer answer = DependecyKnowItAll.answerRepository.getAnswerById(id);
            answers.add(answer);
        }
        return answers;
    }

    private String getStringFromAnswers(Iterable<Answer> answers) {
        StringBuilder sb = new StringBuilder();
        for(Answer answer : answers) {
            sb.append(answer.getId());
            sb.append(";");
        }
        return sb.toString();
    }

    @Override
    public List<Answer> getAnswersUserB() {
        return getAnswersFromString(this.answersUserB);
    }

    public void setAnswersUserB(List<Answer> answers) {
        this.answersUserB = getStringFromAnswers(answers);
    }

    public List<Question> getQuestions() {
        List<Question> questions = new ArrayList<>();
        if(this.questions.isEmpty()) {
            return questions;
        }
        for(String questionId : this.questions.split(";")) {
            long id = Long.parseLong(questionId);
            Question question = DependecyKnowItAll.questionRepository.getQuestionById(id);
            questions.add(question);
        }
        return questions;
    }

    public void setQuestions(Iterable<Question> questions) {
        StringBuilder sb = new StringBuilder();
        for(Question question : questions) {
            sb.append(question.getId());
            sb.append(";");
        }
        this.questions = sb.toString();
    }

    @Override
    public boolean isFinished() {
        return matchFinished;
    }

    @Override
    public void setFinished(boolean matchFinished) {
        this.matchFinished = matchFinished;
    }

    @Override
    public int getCurrentQuestionUserA() {
        return currentQuestionUserA;
    }

    public void setCurrentQuestionUserA(int currentQuestionUserA) {
        this.currentQuestionUserA = currentQuestionUserA;
    }

    @Override
    public int getCurrentQuestionUserB() {
        return currentQuestionUserB;
    }

    public void setCurrentQuestionUserB(int currentQuestionUserB) {
        this.currentQuestionUserB = currentQuestionUserB;
    }
}
