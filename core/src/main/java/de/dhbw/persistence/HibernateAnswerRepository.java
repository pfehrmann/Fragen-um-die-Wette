package de.dhbw.persistence;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.*;

import org.hibernate.search.annotations.Indexed;

import de.dhbw.core.Answer;
import de.dhbw.core.AnswerRepository;
import de.dhbw.core.DependecyKnowItAll;
import de.dhbw.core.Identifiable;

/**
 * This class implements a AnswerRepository for hibernate
 * Created by phili on 06.04.2017.
 */
public class HibernateAnswerRepository implements AnswerRepository {
    public HibernateAnswerRepository() {
    }

    public Answer getAnswerById(long id) {
        return DependecyKnowItAll.manager.find(HibernateAnswer.class, id).getAnswer();
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
        DependecyKnowItAll.manager.getTransaction().begin();
        HibernateAnswer hibernateAnswer = HibernateAnswer.fromAnswer(answer);
        DependecyKnowItAll.manager.persist(hibernateAnswer);
        DependecyKnowItAll.manager.getTransaction().commit();
        answer.setId(hibernateAnswer.getId());
    }
}

@Entity
@Indexed
@Cacheable(false)
class HibernateAnswer implements Identifiable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String text;

    private Long questionId;

    public HibernateAnswer(final long id, final String text) {
        this.id = id;
        this.text = text;
    }

    public HibernateAnswer() {
    }

    public static HibernateAnswer fromAnswer(Answer answer) {
        HibernateAnswer hibernateAnswer;
        try {
            hibernateAnswer = new HibernateAnswer(answer.getId(), answer.getAnswerText());
        } catch (NullPointerException e) {
            hibernateAnswer = new HibernateAnswer();
            hibernateAnswer.setAnswerText(answer.getAnswerText());
        }
        return hibernateAnswer;
    }

    public String getAnswerText() {
        return text;
    }

    public void setAnswerText(String answerText) {
        this.text = answerText;
    }

    public Long getId() {
        return id;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setQuestion(HibernateQuestion question) {
        this.questionId = question.getId();
    }

    public Answer getAnswer() {
        return new Answer(id, text);
    }
}