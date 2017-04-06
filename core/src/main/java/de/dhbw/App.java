package de.dhbw;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import de.dhbw.core.Answer;
import de.dhbw.core.AnswerRepository;
import de.dhbw.persistence.HibernateAnswerRepository;
import de.dhbw.web.RestServer;

/**
 * Hello world!
 *
 */
public class App 
{
    private EntityManagerFactory factory;
    private EntityManager manager;
    private RestServer restServer;

    public static void main( String[] args )
    {
        App app = new App();
        try {
            app.initialize();
        } catch (Exception e) {
            e.printStackTrace();
        }
        // app.put(new Answer("Hallo"));
    }

    private void initialize() throws Exception {
        factory = Persistence.createEntityManagerFactory("cassandra");
        manager = factory.createEntityManager();

        // Create Repositories for hibernate
        AnswerRepository answerRepository = new HibernateAnswerRepository(manager);

        // Start the rest server and supply repositories
        restServer = new RestServer(answerRepository);
    }

    private void put(Answer answer) {
        manager.getTransaction().begin();
        manager.persist(answer);
        manager.getTransaction().commit();
    }
}
