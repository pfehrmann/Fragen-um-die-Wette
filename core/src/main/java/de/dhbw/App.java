package de.dhbw;

import de.dhbw.core.Answer;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Hello world!
 *
 */
public class App 
{
    private EntityManagerFactory factory;
    private EntityManager manager;

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
    }

    private void put(Answer answer) {
        manager.getTransaction().begin();
        manager.persist(answer);
        manager.getTransaction().commit();
    }
}
