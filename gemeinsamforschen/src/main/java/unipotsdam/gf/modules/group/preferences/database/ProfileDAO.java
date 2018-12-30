package unipotsdam.gf.modules.group.preferences.database;

import unipotsdam.gf.modules.group.preferences.excel.ItemSet;
import unipotsdam.gf.modules.group.preferences.survey.SurveyData;
import unipotsdam.gf.modules.project.Project;
import unipotsdam.gf.modules.project.ProjectDAO;
import unipotsdam.gf.modules.user.UserProfile;
import unipotsdam.gf.mysql.MysqlConnect;
import unipotsdam.gf.mysql.VereinfachtesResultSet;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ProfileDAO {
    @Inject
    private MysqlConnect connect;

    @Inject
    private ProjectDAO projectDAO;

    /**
     * helper function for persisting questions
     *
     * @param profileQuestion
     * @return the id of the created question
     */
    private int persistHelper(ProfileQuestion profileQuestion) {

        int result = -1;
        connect.connect();

        String germanQuestion = profileQuestion.getQuestion();
        String englishQuestion = profileQuestion.getQuestion_en();
        String subvariable = profileQuestion.getSubvariable();
        int scaleSize = profileQuestion.getScaleSize();
        Boolean polarity = true;
        if (profileQuestion instanceof ScaledProfileQuestion) {
            polarity = ((ScaledProfileQuestion) profileQuestion).getPolarity();
        }
        String query =
                "INSERT INTO profilequestions (`scaleSize`, `question`, `question_en`, `subvariable`, " + "`polarity`) values (?,?,?,?,?)";
        result = connect.issueInsertStatementWithAutoincrement(query, scaleSize, germanQuestion, englishQuestion,
                subvariable, polarity);

        connect.close();

        return result;
    }

    /**
     * persist questions with enumeration
     *
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
     *
     * @param scaledProfileQuestion
     */
    public void persist(ScaledProfileQuestion scaledProfileQuestion) {
        persistHelper(scaledProfileQuestion);
    }

    /**
     * persist an answer
     *
     * @param profileQuestionAnswer
     */
    public void persist(ProfileQuestionAnswer profileQuestionAnswer) {
        connect.connect();
        String query =
                "INSERT INTO profilequestionanswer (`profileQuestionId`,`answerIndex`, `selectedAnswer`, " + "`userEmail`) values (?,?,?,?)";
        connect.issueInsertOrDeleteStatement(query, profileQuestionAnswer.getQuestion().getId(),
                profileQuestionAnswer.getAnswerIndex(), profileQuestionAnswer.getSelectedAnswer(),
                profileQuestionAnswer.getUser().getEmail());
        connect.close();
    }

    /**
     * get all the questions
     *
     * @return
     */
    public java.util.List<ProfileQuestion> getQuestions() {
        ArrayList<ProfileQuestion> profileQuestions = new ArrayList<>();
        connect.connect();

        String query =
                "SELECT q.id,scaleSize, subvariable, question, question_en, name from profilequestions q" + " LEFT JOIN profilequestionoptions o on q.id = o.profileQuestionId";
        VereinfachtesResultSet vereinfachtesResultSet = connect.issueSelectStatement(query);
        HashMap<Integer, ArrayList<String>> optionMap = new HashMap<>();
        List<ProfileQuestion> tmpList = new ArrayList<>();
        while (vereinfachtesResultSet.next()) {
            String optionName = vereinfachtesResultSet.getString("name");
            int questionId = vereinfachtesResultSet.getInt("id");
            int scaleSize = vereinfachtesResultSet.getInt("scaleSize");
            String question = vereinfachtesResultSet.getString("question");
            String subvariable = vereinfachtesResultSet.getString("subvariable");
            String question_en = vereinfachtesResultSet.getString("question_en");
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
            ProfileQuestion profileQuestion = new ProfileQuestion(scaleSize, question, question_en, subvariable);
            profileQuestion.setId(questionId);
            tmpList.add(profileQuestion);
        }

        for (ProfileQuestion question : tmpList) {
            if (optionMap.containsKey(question.getId())) {
                EnumeratedProfileQuestion enumeratedProfileQuestion = new EnumeratedProfileQuestion();
                enumeratedProfileQuestion.setScaleSize(question.getScaleSize());
                enumeratedProfileQuestion.setOptions(optionMap.get(question.getId()));
                enumeratedProfileQuestion.setQuestion(question.getQuestion());
                profileQuestions.add(enumeratedProfileQuestion);
            } else {
                profileQuestions.add(question);
            }
        }

        connect.close();
        return profileQuestions;
    }

    public List<ProfileQuestionRelation> getProfileRelations() {
        ArrayList<ProfileQuestionRelation> questionRelations = new ArrayList<>();
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
            questionRelations
                    .add(new ProfileQuestionRelation(profileQuestion1, profileQuestion2, profileQuestionRelationType));
        }

        connect.close();
        return questionRelations;
    }

    public void persistProfileRelation(ProfileQuestion firstQuestion, ProfileQuestion secondQuestion) {
        // TODO implement
        connect.connect();

        connect.close();
    }

    /**
     * persist a variable for group formation
     *
     * @param itemSet
     */
    public void persistProfileVariable(ItemSet itemSet) {
        String variable = itemSet.getVariable();
        String subvariable = itemSet.getSubVariable();
        String context = itemSet.getContext();
        String variableDefinition = itemSet.getVariableDefinition();
        String subvariableweight = "1";
        String variableweight = "1";

        connect.connect();
        String query =
                "INSERT INTO profilevariables (`variable`, `subvariable`, `variableDefinition`, `context`, " + "`variableweight`, `subvariableweight`) values (?,?,?,?,?,?)";
        connect.issueInsertOrDeleteStatement(query, variable, subvariable, variableDefinition, context, variableweight,
                subvariableweight);
        connect.close();

    }

    public HashMap<Project, List<ProfileQuestion>> getSelectedQuestions() {
        HashMap<Project, List<ProfileQuestion>> profileQuestions = new HashMap<>();
        connect.connect();
        String query =
                "Select pq.id,p.name, pq.question, pq.question_en, pq.subvariable from projects p " + "join " +
                        "surveyitemsselected sis on p.name = sis.projectname " + "join profilequestions pq on pq.id = sis.profilequestionid";
        VereinfachtesResultSet vereinfachtesResultSet = connect.issueSelectStatement(query);
        while (vereinfachtesResultSet.next()) {
            Project project = new Project(vereinfachtesResultSet.getString("name"));
            ProfileQuestion profileQuestion = new ProfileQuestion(5, vereinfachtesResultSet.getString("question"),
                    vereinfachtesResultSet.getString("question_en"), vereinfachtesResultSet.getString("subvariable"));
            profileQuestion.setId(vereinfachtesResultSet.getInt("id"));
            if (profileQuestions.keySet().contains(project)) {
                List<ProfileQuestion> profileQuestions1 = profileQuestions.get(project);
                profileQuestions1.add(profileQuestion);
                // this will overwrite the other questions
                profileQuestions.put(project, profileQuestions1);
            } else {
                ArrayList<ProfileQuestion> profileQuestions2 = new ArrayList<>();
                profileQuestions2.add(profileQuestion);
                profileQuestions.put(project, profileQuestions2);
            }
        }
        connect.close();
        return profileQuestions;
    }

    public void addItemsToProject(Project project, List<ProfileQuestion> questions) {

        ArrayList<Integer> questionids = new ArrayList<>();

        // persist the questions
        for (ProfileQuestion question : questions) {
            int i = persistHelper(question);
            questionids.add(i);
        }

        connect.connect();
        String query = "INSERT INTO surveyitemsselected (`projectname`, `profilequestionid`) values (?,?)";
        for (Integer questionid : questionids) {
            connect.issueInsertOrDeleteStatement(query, project.getName(), questionid);
        }
        connect.close();
    }

    public void save(UserProfile profile) {
        HashMap<String, String> data = profile.getData();
        for (String key : data.keySet()) {
            String value = data.get(key);
            int questionId = Integer.parseInt(key.trim());
            ProfileQuestionAnswer profileQuestionAnswer = new ProfileQuestionAnswer();
            profileQuestionAnswer.setQuestion(new ProfileQuestion(questionId));
            profileQuestionAnswer.setAnswerIndex(Integer.parseInt(value));
            profileQuestionAnswer.setUser(profile.getUser());
            persist(profileQuestionAnswer);
        }

    }
}
