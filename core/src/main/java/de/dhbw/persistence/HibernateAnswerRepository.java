package de.dhbw.persistence;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.EntityManager;

import de.dhbw.core.Answer;
import de.dhbw.core.AnswerRepository;

/**
 * This class implements a AnswerRepository for hibernate
 * Created by phili on 06.04.2017.
 */
public class HibernateAnswerRepository implements AnswerRepository {
    private final EntityManager manager;

    public HibernateAnswerRepository(EntityManager manager) {
        this.manager = manager;
    }

    public Answer getAnswerById(long id) {
        return manager.find(Answer.class, id);
    }

    public Collection<Answer> getAnswersById(long... ids) {
        List<Long> idList = new ArrayList<>();
        for(long id : ids) {
            idList.add(id);
        }
        return getAnswersById(idList);
    }

    public Collection<Answer> getAnswersById(List<Long> ids) {
        List<Answer> answers = new ArrayList<>();
        for (long id : ids ) {
            answers.add(getAnswerById(id));
        }
        return answers;
    }

    @Override
    public void persist(Answer answer) {
        manager.getTransaction().begin();
        manager.persist(answer);
        manager.getTransaction().commit();
    }
}
