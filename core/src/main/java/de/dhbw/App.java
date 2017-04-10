package de.dhbw;

import java.util.ArrayList;
import java.util.List;

import de.dhbw.core.*;
import de.dhbw.persistence.HibernateAnswerRepository;
import de.dhbw.persistence.HibernateMatchRepository;
import de.dhbw.persistence.HibernateQuestionRepository;
import de.dhbw.persistence.HibernateUserRepository;
import de.dhbw.web.RestServer;

/**
 * Hello world!
 *
 */
public class App 
{
    private RestServer restServer;

    private QuestionRepository questionRepository;
    private UserRepository userRepository;
    private AnswerRepository answerRepository;
    private MatchRepository matchRepository;

    public static void main( String[] args )
    {
        App app = new App();
        try {
            app.initialize();
        } catch (Exception e) {
            e.printStackTrace();
        }
        app.seedDatabase();
    }

    private void seedDatabase() {
        Question question = new Question();
        question.setQuestionText("Wann wurde FUDW \"erfunden\"?");
        Answer answer = new Answer();
        answer.setAnswerText("2017");
        List<Answer> answers = new ArrayList<>();
        answers.add(answer);
        question.setPossibleAnswers(answers);
        question.setCorrectAnswer(answer);
        put(answer);
        put(question);

        User userA = new User("Philipp");
        User userB = new User("Leif");
        put(userA);
        put(userB);
    }

    private void initialize() throws Exception {
        // Create Repositories for hibernate
        answerRepository = new HibernateAnswerRepository();
        userRepository = new HibernateUserRepository();
        questionRepository = new HibernateQuestionRepository();
        matchRepository = new HibernateMatchRepository();

        // Well some "Dependency Injection"... not
        DependecyKnowItAll.answerRepository = answerRepository;
        DependecyKnowItAll.userRepository = userRepository;
        DependecyKnowItAll.questionRepository = questionRepository;
        DependecyKnowItAll.matchRepository = matchRepository;

        // Start the rest server and supply repositories
        restServer = new RestServer(answerRepository, matchRepository, userRepository);
    }

    private void put(Question question) {
        questionRepository.persistQuestion(question);
    }

    private void put(Answer answer) {
        answerRepository.persist(answer);
    }
    private void put(User user) {
        userRepository.persistUser(user);
    }
    private void put(Match match) {
        matchRepository.persistMatch(match);
    }
}
