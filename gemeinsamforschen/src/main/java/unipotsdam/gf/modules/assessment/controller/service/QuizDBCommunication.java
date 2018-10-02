package unipotsdam.gf.modules.assessment.controller.service;

import unipotsdam.gf.mysql.MysqlConnect;
import unipotsdam.gf.mysql.VereinfachtesResultSet;
import unipotsdam.gf.modules.assessment.controller.model.Quiz;

import javax.annotation.ManagedBean;
import javax.annotation.Resource;
import javax.inject.Singleton;
import java.util.ArrayList;

@ManagedBean
@Resource
@Singleton
public class QuizDBCommunication {
    Quiz getQuizByProjectQuizId(String projectName, String quizId, String author) {
        MysqlConnect connect = new MysqlConnect();
        connect.connect();
        String mysqlRequest = "SELECT * FROM `quiz` WHERE `projectName`=? AND `question`=? AND `author`=?";
        VereinfachtesResultSet vereinfachtesResultSet =
                connect.issueSelectStatement(mysqlRequest, projectName, quizId, author);
        boolean next = vereinfachtesResultSet.next();
        String question = "";
        ArrayList<String> correctAnswers = new ArrayList<>();
        ArrayList<String> incorrectAnswers = new ArrayList<>();
        String answer;
        Boolean correct;
        String mcType = "";
        while (next) {
            mcType = vereinfachtesResultSet.getString("mcType");
            question = vereinfachtesResultSet.getString("question");
            answer = vereinfachtesResultSet.getString("answer");
            correct = vereinfachtesResultSet.getBoolean("correct");
            if (correct) {
                correctAnswers.add(answer);
            } else {
                incorrectAnswers.add(answer);
            }
            next = vereinfachtesResultSet.next();
        }
        Quiz quiz = new Quiz(mcType, question, correctAnswers, incorrectAnswers);
        connect.close();
        return quiz;
    }

    ArrayList<Quiz> getQuizByProjectId(String projectName) {
        String mysqlRequest = "SELECT * FROM quiz where projectName= ?";
        return RequestToQuizList(mysqlRequest, projectName);
    }


    ArrayList<Quiz> getQuizByProjectIdAuthor(String projectName, String author){
        String mysqlRequest = "SELECT * FROM quiz where projectName= ? AND author=?";
        return RequestToQuizList(mysqlRequest, projectName, author);
    }

    private ArrayList<Quiz> RequestToQuizList(String sqlRequest, Object ... params) {
        MysqlConnect connect = new MysqlConnect();

        connect.connect();
        VereinfachtesResultSet vereinfachtesResultSet =
                connect.issueSelectStatement(sqlRequest, params);
        boolean next = vereinfachtesResultSet.next();
        ArrayList<Quiz> result = new ArrayList<>();
        String question;
        ArrayList<String> correctAnswers = new ArrayList<>();
        ArrayList<String> incorrectAnswers = new ArrayList<>();
        String answer;
        String oldQuestion = "";
        Boolean correct;
        String mcType = "";
        Quiz quiz;
        while (next) {
            mcType = vereinfachtesResultSet.getString("mcType");
            question = vereinfachtesResultSet.getString("question");
            answer = vereinfachtesResultSet.getString("answer");
            correct = vereinfachtesResultSet.getBoolean("correct");
            if (oldQuestion.equals(question)) {
                if (correct) {
                    correctAnswers.add(answer);
                } else {
                    incorrectAnswers.add(answer);
                }
            } else {
                quiz = new Quiz(mcType, oldQuestion, correctAnswers, incorrectAnswers);
                result.add(quiz);
                correctAnswers = new ArrayList<>();
                incorrectAnswers = new ArrayList<>();
                if (correct) {
                    correctAnswers.add(answer);
                } else {
                    incorrectAnswers.add(answer);
                }

            }
            oldQuestion = question;
            next = vereinfachtesResultSet.next();
        }
        quiz = new Quiz(mcType, oldQuestion, correctAnswers, incorrectAnswers);
        result.add(quiz);
        return result;
    }

    public void deleteQuiz(String quizId) {
        MysqlConnect connect = new MysqlConnect();
        connect.connect();
        String mysqlRequest = "DELETE FROM quiz where question = (?)";
        connect.issueInsertOrDeleteStatement(mysqlRequest, quizId);
        connect.close();
    }

    public void createQuiz(Quiz quiz, String author, String projectName) {
        MysqlConnect connect = new MysqlConnect();
        connect.connect();
        String mcType;
        String question;
        String answer;
        ArrayList<String> correctAnswers = quiz.getCorrectAnswers();
        for (String correctAnswer : correctAnswers) {
            answer = correctAnswer;
            mcType = quiz.getType();
            question = quiz.getQuestion();
            String mysqlRequest = "INSERT INTO `quiz`(`author`, `projectName`, `question`, `mcType`, `answer`, `correct`) VALUES (?,?,?,?,?,?)";
            connect.issueInsertOrDeleteStatement(mysqlRequest, author, projectName, question, mcType, answer, true);
        }
        ArrayList<String> incorrectAnswers = quiz.getIncorrectAnswers();
        for (String incorrectAnswer : incorrectAnswers) {
            answer = incorrectAnswer;
            mcType = quiz.getType();
            question = quiz.getQuestion();
            String mysqlRequest = "INSERT INTO `quiz`(`author`, `projectName`, `question`, `mcType`, `answer`, `correct`) VALUES (?,?,?,?,?,?)";
            connect.issueInsertOrDeleteStatement(mysqlRequest, author, projectName, question, mcType, answer, false);
        }
        connect.close();
    }
}
