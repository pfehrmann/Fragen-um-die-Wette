package de.dhbw.persistence;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.search.annotations.Indexed;

import de.dhbw.core.Answer;
import de.dhbw.core.DependecyKnowItAll;
import de.dhbw.core.Question;
import de.dhbw.core.QuestionRepository;

/**
 * This class implements a AnswerRepository for hibernate
 * Created by phili on 06.04.2017.
 */
public class HibernateQuestionRepository implements QuestionRepository {
    public HibernateQuestionRepository() {

    }

    @Override
    public Question getQuestionById(long id) {
        return DependecyKnowItAll.manager.find(HibernateQuestion.class, id);
    }

    @Override
    public void persistQuestion(Question question) {
        DependecyKnowItAll.manager.getTransaction().begin();
        HibernateQuestion hibernateQuestion = HibernateQuestion.getHibernateQuestion(question);
        DependecyKnowItAll.manager.persist(hibernateQuestion);
        DependecyKnowItAll.manager.getTransaction().commit();
        question.setId(hibernateQuestion.getId());
    }

    @Override
    public Collection<Question> getRandomQuestions(int count) {
        // TODO this only returns the first count questions.
        String nativeQuery = "SELECT * " +
                "FROM \"HibernateQuestion\" " +
                "WHERE \"id\" > 0 " +
                "ALLOW FILTERING";
        List resultList = DependecyKnowItAll.manager.createNativeQuery(nativeQuery, HibernateQuestion.class)
                .setMaxResults(count).getResultList();
        List<Question> questions = new ArrayList<>();
        for (Object result : resultList) {
            questions.add((HibernateQuestion)result);
        }
        return questions;
    }
}

@Entity
@Indexed
class HibernateQuestion extends Question{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String questionText;

    private String possibleAnswers;

    private int correctAnswer;

    public HibernateQuestion(Long id, String questionText, List<Answer> possibleAnswers, Answer correctAnswer) {
        this.id = id;
        this.questionText = questionText;
        this.setPossibleAnswers(possibleAnswers);
        this.correctAnswer = possibleAnswers.indexOf(correctAnswer);
    }

    public HibernateQuestion() {
    }

    public static HibernateQuestion getHibernateQuestion(Question q) {
        HibernateQuestion hibernateQuestion;
        try {
            // If id is not set, this will throw an error.
            hibernateQuestion = new HibernateQuestion(q.getId(), q.getQuestionText(), q.getPossibleAnswers(), q.getCorrectAnswer());
        } catch (NullPointerException e) {
            hibernateQuestion = new HibernateQuestion();
            hibernateQuestion.setPossibleAnswers(q.getPossibleAnswers());
            hibernateQuestion.setQuestionText(q.getQuestionText());
            hibernateQuestion.setCorrectAnswer(q.getCorrectAnswer(), q.getPossibleAnswers());
        }
        return hibernateQuestion;
    }

    public List<Answer> getPossibleAnswers() {
        List<Answer> possibleAnswers = new ArrayList<>();
        for (String answerId : this.possibleAnswers.split(";")) {
            long id = Long.parseLong(answerId);
            Answer answer = DependecyKnowItAll.answerRepository.getAnswerById(id);
            possibleAnswers.add(answer);
        }
        return possibleAnswers;
    }

    public void setPossibleAnswers(List<Answer> possibleAnswers) {
        if(null == possibleAnswers) {
            return;
        }
        StringBuilder possibleAnswersSb = new StringBuilder();
        for(Answer answer : possibleAnswers) {
            possibleAnswersSb.append(HibernateAnswer.fromAnswer(answer).getId());
            possibleAnswersSb.append(";");
        }
        this.possibleAnswers = possibleAnswersSb.toString();
    }

    public void setCorrectAnswer(Answer answer, List<Answer> possibleAnswers) {
        if(possibleAnswers != null && possibleAnswers.isEmpty()) {
            throw new RuntimeException("No possible answers given. Set these first.");
        }
        correctAnswer = getPossibleAnswers().indexOf(answer);
    }

    @Override
    public Answer getCorrectAnswer() {
        return getPossibleAnswers().get(correctAnswer);
    }

    @Override
    public void setCorrectAnswer(Answer answer) {
        if("".equals(possibleAnswers)) {
            throw new RuntimeException("No possible answers given. Set these first.");
        }
        int index = getPossibleAnswers().indexOf(answer);
        if(index < 0) {
            throw new IllegalArgumentException("Correct Answer not in possible answers.");
        }
        correctAnswer = index;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    @Override
    public Long getId() {
        return id;
    }
}