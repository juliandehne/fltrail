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
    public Quiz getQuizByProjectGroupId(String projectId, String quizId){
        MysqlConnect connect = new MysqlConnect();
        connect.connect();
        String mysqlRequest = "SELECT * FROM quiz where projectId=? AND question=?";
        VereinfachtesResultSet vereinfachtesResultSet =
                connect.issueSelectStatement(mysqlRequest, projectId,quizId);
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
                result.add(new Quiz(mcType,question, correctAnswers, incorrectAnswers));
                correctAnswers.clear();
                incorrectAnswers.clear();
                if (correct){
                    correctAnswers.add(answer);
                }else{
                    incorrectAnswers.add(answer);
                }

            }
            oldQuestion = question;
            next = vereinfachtesResultSet.next();
        }
        return result;
    }
}
