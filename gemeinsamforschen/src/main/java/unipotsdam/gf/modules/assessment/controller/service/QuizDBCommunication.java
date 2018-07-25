package unipotsdam.gf.modules.assessment.controller.service;

import unipotsdam.gf.core.database.mysql.MysqlConnect;
import unipotsdam.gf.core.database.mysql.VereinfachtesResultSet;
import unipotsdam.gf.modules.assessment.controller.model.Quiz;

import javax.annotation.ManagedBean;
import javax.annotation.Resource;
import javax.inject.Singleton;
import java.util.ArrayList;

@ManagedBean
@Resource
@Singleton
public class QuizDBCommunication {
    public Quiz getQuizByProjectQuizId(String projectId, String quizId, String author){
        MysqlConnect connect = new MysqlConnect();
        connect.connect();
        String mysqlRequest = "SELECT * FROM `quiz` WHERE `projectId`=? AND `question`=? AND `author`=?";
        VereinfachtesResultSet vereinfachtesResultSet =
                connect.issueSelectStatement(mysqlRequest, projectId,quizId,author);
        boolean next = vereinfachtesResultSet.next();
        String question = "";
        ArrayList<String> correctAnswers = new ArrayList<String>();
        ArrayList<String> incorrectAnswers = new ArrayList<String>();
        String answer;
        Boolean correct;
        String mcType = "";
        while (next) {
            mcType = vereinfachtesResultSet.getString("mcType");
            question = vereinfachtesResultSet.getString("question");
            answer = vereinfachtesResultSet.getString("answer");
            correct = vereinfachtesResultSet.getBoolean("correct");
            if (correct){
                correctAnswers.add(answer);
            }else{
                incorrectAnswers.add(answer);
            }
            next = vereinfachtesResultSet.next();
        }
        Quiz quiz = new Quiz(mcType,question, correctAnswers, incorrectAnswers);
        connect.close();
        return quiz;
    }

    public ArrayList<Quiz> getQuizByProjectId(String projectId) {
        MysqlConnect connect = new MysqlConnect();
        ArrayList<Quiz> result= new ArrayList<Quiz>();
        connect.connect();
        String mysqlRequest = "SELECT * FROM quiz where projectId= ?";
        VereinfachtesResultSet vereinfachtesResultSet =
                connect.issueSelectStatement(mysqlRequest, projectId);
        boolean next = vereinfachtesResultSet.next();
        String question = "";
        ArrayList<String> correctAnswers = new ArrayList<String>();
        ArrayList<String> incorrectAnswers = new ArrayList<String>();
        String answer;
        String oldQuestion="";
        Boolean correct;
        String mcType = "";
        Quiz quiz =null;
        while (next) {
            mcType = vereinfachtesResultSet.getString("mcType");
            question = vereinfachtesResultSet.getString("question");
            answer = vereinfachtesResultSet.getString("answer");
            correct = vereinfachtesResultSet.getBoolean("correct");
            if (oldQuestion.equals(question)){
                if (correct){
                    correctAnswers.add(answer);
                }else{
                    incorrectAnswers.add(answer);
                }
            }else{
                quiz = new Quiz(mcType,oldQuestion, correctAnswers, incorrectAnswers);
                result.add(quiz);
                quiz=null;
                correctAnswers=new ArrayList<String>();
                incorrectAnswers=new ArrayList<String>();
                if (correct){
                    correctAnswers.add(answer);
                }else{
                    incorrectAnswers.add(answer);
                }

            }
            oldQuestion = question;
            next = vereinfachtesResultSet.next();
        }
        quiz = new Quiz(mcType,oldQuestion, correctAnswers, incorrectAnswers);
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

    public void createQuiz(Quiz quiz, String author, String projectId) {
        MysqlConnect connect = new MysqlConnect();
        connect.connect();
        String mcType;
        String question;
        String answer;
        boolean correct;
        ArrayList<String> correctAnswers = quiz.getCorrectAnswers();
        for (int i=0; i<correctAnswers.size(); i++) {
            answer = correctAnswers.get(i);
            mcType = quiz.getType();
            question = quiz.getQuestion();
            correct = true;
            String mysqlRequest = "INSERT INTO `quiz`(`author`, `projectId`, `question`, `mcType`, `answer`, `correct`) VALUES (?,?,?,?,?,?)";
            connect.issueInsertOrDeleteStatement(mysqlRequest, author, projectId, question, mcType, answer, correct);
        }
        ArrayList<String> incorrectAnswers = quiz.getIncorrectAnswers();
        for (int i=0; i<incorrectAnswers.size(); i++) {
            answer = incorrectAnswers.get(i);
            mcType = quiz.getType();
            question = quiz.getQuestion();
            correct = false;
            String mysqlRequest = "INSERT INTO `quiz`(`author`, `projectId`, `question`, `mcType`, `answer`, `correct`) VALUES (?,?,?,?,?,?)";
            connect.issueInsertOrDeleteStatement(mysqlRequest, author, projectId, question, mcType, answer, correct);
        }
        connect.close();
    }
}
