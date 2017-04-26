package de.dhbw;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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
        app.inputLoop();
    }

    private void inputLoop() {
        Scanner scan = new Scanner(System.in);

        while(true) {
            System.out.println("Befehl eingeben");
            String line = scan.nextLine();
            if("seed".equals(line)) {
                seedDatabase();
            } else if (line.startsWith("question")) {
                String questionSplit[] = line.split(";;");
                Question question = new Question();
                question.setQuestionText(questionSplit[1]);

                List<Answer> answers = new ArrayList<>();
                Answer correct = new Answer();
                correct.setAnswerText(questionSplit[2]);
                answers.add(correct);

                Answer possible1 = new Answer();
                possible1.setAnswerText(questionSplit[3]);
                answers.add(possible1);

                Answer possible2 = new Answer();
                possible2.setAnswerText(questionSplit[4]);
                answers.add(possible2);

                Answer possible3 = new Answer();
                possible3.setAnswerText(questionSplit[5]);
                answers.add(possible3);

                question.setCorrectAnswer(correct);
                question.setPossibleAnswers(answers);

                put(correct);
                put(possible1);
                put(possible2);
                put(possible3);
                put(question);
            }
        }
    }

    private void seedDatabase() {
        Question question = new Question();
        question.setQuestionText("Wann wurde FUDW \"erfunden\"?");

        List<Answer> answers = new ArrayList<>();

        Answer answer = new Answer();
        answer.setAnswerText("2016");
        answers.add(answer);
        put(answer);

        answer = new Answer();
        answer.setAnswerText("2015");
        answers.add(answer);
        put(answer);

        answer = new Answer();
        answer.setAnswerText("2018");
        answers.add(answer);
        put(answer);

        answer = new Answer();
        answer.setAnswerText("2017");
        answers.add(answer);
        put(answer);

        question.setPossibleAnswers(answers);
        question.setCorrectAnswer(answer);
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
