package unipotsdam.gf.modules.group.preferences.database;

import unipotsdam.gf.mysql.MysqlConnect;
import unipotsdam.gf.mysql.VereinfachtesResultSet;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ProfileDAO {
    @Inject
    private MysqlConnect connect;

    /**
     * helper function for persisting questions
     * @param profileQuestion
     * @return the id of the created question
     */
    private int persistHelper(ProfileQuestion profileQuestion) {

        int result = -1;
        connect.connect();

        String query = "INSERT INTO profilequestions (`scaleSize`, `question`) values(?,?)";
        result = connect.issueInsertStatementWithAutoincrement(query, profileQuestion.getScaleSize(),
                profileQuestion.getQuestion());

        connect.close();

        return result;
    }

    /**
     * persist questions with enumeration
     * @param enumeratedProfileQuestion
     */
    public void persist(EnumeratedProfileQuestion enumeratedProfileQuestion) {
        int questionId = persistHelper(enumeratedProfileQuestion);
        connect.connect();
        String query = "INSERT INTO profilequestionoptions (`profileQuestionId`, `name`) values (?,?)";
        List<String> options = enumeratedProfileQuestion.getOptions();
        for (String option : options) {
            connect.issueInsertOrDeleteStatement(query, questionId, option);
        }
        connect.close();
    }

    /**
     * persist questions with scale i.e. 1 to 5
     * @param scaledProfileQuestion
     */
    public void persist(ScaledProfileQuestion scaledProfileQuestion) {
        persistHelper(scaledProfileQuestion);
    }

    /**
     * persist an answer
     * @param profileQuestionAnswer
     */
    public void persist(ProfileQuestionAnswer profileQuestionAnswer) {
        connect.connect();
        String query =
                "INSERT INTO profilequestionanswer (`profileQuestionId`,`answerIndex`, `selectedAnswer`, " + "`userEmail` values (?,?,?,?)";
        connect.issueInsertOrDeleteStatement(query, profileQuestionAnswer.getQuestion().getId(),
                profileQuestionAnswer.getAnswerIndex(), profileQuestionAnswer.getSelectedAnswer(),
                profileQuestionAnswer.getUser().getEmail());
    }

    /**
     * get all the questions
     * @return
     */
    public java.util.List<ProfileQuestion> getQuestions() {
        ArrayList<ProfileQuestion> profileQuestions = new ArrayList<>();
        connect.connect();

        String query =
                "SELECT (q.id,q.scaleSize, q.question, o.name) from profilequestions q LEFT JOIN " + "profilequestionoptions o where q.id = o" + ".profileQuestionId group by q.id";
        VereinfachtesResultSet vereinfachtesResultSet = connect.issueSelectStatement(query);
        HashMap<Integer, ArrayList<String>> optionMap = new HashMap<>();
        List<ProfileQuestion> tmpList = new ArrayList<>();
        while (vereinfachtesResultSet.next()) {
            String optionName = vereinfachtesResultSet.getString("name");
            int questionId = vereinfachtesResultSet.getInt("id");
            int scaleSize = vereinfachtesResultSet.getInt("scaleSize");
            String question = vereinfachtesResultSet.getString("question");
            if (optionName != null) {
                if (optionMap.containsKey(optionName)) {
                    ArrayList<String> optionList = optionMap.get(optionName);
                    optionList.add(optionName);
                    optionMap.put(questionId, optionList);
                } else {
                    ArrayList<String> optionList = new ArrayList<>();
                    optionList.add(optionName);
                    optionMap.put(questionId, optionList);
                }
            }
            tmpList.add(new ProfileQuestion(questionId, scaleSize, question));
        }

        for (ProfileQuestion question : tmpList) {
            if (optionMap.containsKey(question.getId())) {
                EnumeratedProfileQuestion enumeratedProfileQuestion = new EnumeratedProfileQuestion();
                enumeratedProfileQuestion.setScaleSize(question.getScaleSize());
                enumeratedProfileQuestion.setOptions(optionMap.get(question.getId()));
                enumeratedProfileQuestion.setQuestion(question.getQuestion());
                profileQuestions.add(enumeratedProfileQuestion);
            }else {
                profileQuestions.add(question);
            }
        }

        connect.close();
        return profileQuestions;
    }

    public List<ProfileQuestionRelation> getProfileRelations() {
        ArrayList<ProfileQuestionRelation>questionRelations = new ArrayList<>();
        connect.connect();
        String query = "SELCT * from profilequestionrelations";
        VereinfachtesResultSet vereinfachtesResultSet = connect.issueSelectStatement(query);
        while (vereinfachtesResultSet.next()) {
            int firstQuestionId = vereinfachtesResultSet.getInt("firstQuestionId");
            int secondQuestionId = vereinfachtesResultSet.getInt("secondQuestionId");
            String relation = vereinfachtesResultSet.getString("relation");

            ProfileQuestion profileQuestion1 = new ProfileQuestion(firstQuestionId);
            ProfileQuestion profileQuestion2 = new ProfileQuestion(secondQuestionId);
            ProfileQuestionRelationType profileQuestionRelationType = ProfileQuestionRelationType.valueOf(relation);
            questionRelations.add(new ProfileQuestionRelation(profileQuestion1, profileQuestion2,
                    profileQuestionRelationType));
        }

        connect.close();
        return questionRelations;
    }

    public void persistProfileRelation(ProfileQuestion firstQuestion, ProfileQuestion secondQuestion) {
        // TODO implement
        connect.connect();

        connect.close();
    }

}
