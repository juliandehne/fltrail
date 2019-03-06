package unipotsdam.gf.modules.group.preferences.database;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import unipotsdam.gf.modules.group.preferences.excel.ItemSet;
import unipotsdam.gf.modules.group.preferences.groupal.request.*;
import unipotsdam.gf.modules.group.preferences.survey.GroupWorkContext;
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
    public void persist(ProfileQuestionAnswer profileQuestionAnswer, GroupWorkContext groupWorkContext) {
        connect.connect();
        String query;
        if (groupWorkContext == GroupWorkContext.evaluation) {
            query =
                    "INSERT INTO peerAssessmentWorkAnswer (`propertieId`,`answerIndex`, `selectedAnswer`, " + "`userEmail`) values (?,?,?,?)";
        } else {
            query =
                    "INSERT INTO profilequestionanswer (`profileQuestionId`,`answerIndex`, `selectedAnswer`, " + "`userEmail`) values (?,?,?,?)";
        }
        connect.issueInsertOrDeleteStatement(query, profileQuestionAnswer.getQuestion().getId(),
                profileQuestionAnswer.getAnswerIndex(), profileQuestionAnswer.getSelectedAnswer(),
                profileQuestionAnswer.getUser().getEmail());
        connect.close();
    }

    /**
     * get all the questions
     *
     * @param gfc
     * @return
     */
    public java.util.List<ProfileQuestion> getQuestions(
            GroupWorkContext gfc) throws Exception {
        ArrayList<ProfileQuestion> profileQuestions = new ArrayList<>();
        connect.connect();

        String query = "";
        String queryWithFL =
                "SELECT q.id,scaleSize, subvariable, question, question_en, name from profilequestions q" +
                        " LEFT JOIN profilequestionoptions o on q.id = o.profileQuestionId";

        String queryWithoutFL =
                "SELECT q.id,scaleSize, subvariable, question, question_en, name from profilequestions q" +
                        " LEFT JOIN profilequestionoptions o on q.id = o.profileQuestionId" +
                        " LEFT JOIN profilevariables pv on pv.subvariable = pq.subvariable " +
                        " WHERE pv.variable = 'Persönlichkeit'";

        switch (gfc) {
            case fl:
            case evaluation:
                throw new Exception("wrong usage of method");
            case dota:
            case dota_survey_a2:
            case fl_survey_a4:
            case fl_test:
            case other_survey_a2:
            case fl_lausberg:
            case dota_test:
                query = queryWithFL;
                break;
            default:
                query = queryWithoutFL;
        }


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
        Boolean homogeneity = itemSet.getIsHomogenous().trim().equals("true");
        connect.connect();
        String query =
                "INSERT INTO profilevariables (`variable`, `subvariable`, `variableDefinition`, `context`, " + "`variableweight`, `subvariableweight`, `homogeneity`) values (?,?,?,?,?,?,?)";
        connect.issueInsertOrDeleteStatement(query, variable, subvariable, variableDefinition, context, variableweight,
                subvariableweight, homogeneity);
        connect.close();

    }

    public HashMap<Project, List<ProfileQuestion>> getSelectedQuestions(GroupWorkContext groupWorkContext) {
        HashMap<Project, List<ProfileQuestion>> profileQuestions = new HashMap<>();
        connect.connect();
        String query;
        if (groupWorkContext == GroupWorkContext.evaluation) {
            query = "Select id, question, question_en, subvariable from peerAssessmentWorkProperties";
        } else {
            query =
                    "Select pq.id,p.name, pq.question, pq.question_en, pq.subvariable from projects p " + "join surveyitemsselected sis on p.name = sis.projectname " + "join profilequestions pq on pq.id = sis.profilequestionid";
        }
        VereinfachtesResultSet vereinfachtesResultSet = connect.issueSelectStatement(query);
        while (vereinfachtesResultSet.next()) {
            ProfileQuestion profileQuestion = new ProfileQuestion(5, vereinfachtesResultSet.getString("question"),
                    vereinfachtesResultSet.getString("question_en"), vereinfachtesResultSet.getString("subvariable"));
            profileQuestion.setId(vereinfachtesResultSet.getInt("id"));
            Project project;
            if (groupWorkContext != GroupWorkContext.evaluation) {
                project = new Project(vereinfachtesResultSet.getString("name"));
            } else {
                project = new Project();
            }
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

        //ArrayList<Integer> questionids = new ArrayList<>();

        // persist the questions
    /*    for (ProfileQuestion question : questions) {
            //int i = persistHelper(question);
            int i = -1;
            connect.connect();
            connect
            connect.close();
            questionids.add(i);
        }*/

        for (ProfileQuestion question : questions) {
            connect.connect();
            String query =
                    "INSERT INTO surveyitemsselected (`projectname`, `profilequestionid`) " + "select ?, id from profilequestions p where p.question = ?";
            connect.issueInsertOrDeleteStatement(query, project.getName(), question.getQuestion());
            connect.close();
        }
    }

    public void save(UserProfile profile, GroupWorkContext groupWorkContext) {
        HashMap<String, String> data = profile.getData();
        for (String key : data.keySet()) {
            String value = data.get(key);
            int questionId = Integer.parseInt(key.trim());
            ProfileQuestionAnswer profileQuestionAnswer = new ProfileQuestionAnswer();
            profileQuestionAnswer.setQuestion(new ProfileQuestion(questionId));
            profileQuestionAnswer.setAnswerIndex(Integer.parseInt(value));
            profileQuestionAnswer.setUser(profile.getUser());
            persist(profileQuestionAnswer, groupWorkContext);
        }
    }

    /**
     * get participant answers from the db
     *
     * @param project
     * @return
     */
    public ParticipantsHolder getResponses(Project project) {
        ArrayList<UsedCriterion> usedCriterions = new ArrayList<>();
        ParticipantsHolder participantsHolder = new ParticipantsHolder();
        HashMap<String, ArrayList<Criterion>> participantsMap = new HashMap<>();
        HashMap<String, Integer> ids = new HashMap<>();

        connect.connect();
        String query =
                "SELECT pv.homogeneity, pqa.answerIndex, pqa.userEmail, pq.subvariable, pq.polarity, u.id " + "from profilequestionanswer pqa " + " join profilequestions pq on pq.id = pqa.profileQuestionId" + " join surveyitemsselected sis on sis.profilequestionid = pq.id" + " join users u on u.email = pqa.userEmail" + " join projectuser pu on u.email=pu.userEmail and pu.projectname = ?" + " join profilevariables pv on pv.subvariable = pq.subvariable ";
        VereinfachtesResultSet vereinfachtesResultSet = connect.issueSelectStatement(query, project.getName());
        int i = 0;
        while (vereinfachtesResultSet.next()) {
            String subvariable = vereinfachtesResultSet.getString("subvariable");
            String isHomogenous = vereinfachtesResultSet.getInt("homogeneity") == 1 ? "true" : "false";
            Boolean polarity = vereinfachtesResultSet.getBoolean("polarity");
            int answerIndex = vereinfachtesResultSet.getInt("answerIndex");
            String email = vereinfachtesResultSet.getString("userEmail");
            int userId = vereinfachtesResultSet.getInt("id");

            UsedCriterion usedCriterionMain = fillUsedCriterion(usedCriterions, subvariable, isHomogenous);
            fillParticipantMap(participantsMap, ids, email, answerIndex, userId, subvariable, isHomogenous, polarity,
                    usedCriterionMain);

        }
        connect.close();

        fillPojo(usedCriterions, participantsHolder, participantsMap, ids);

        return participantsHolder;
    }


    private void fillParticipantMap(
            HashMap<String, ArrayList<Criterion>> participantsMap, HashMap<String, Integer> ids, String userEmail,
            int answerIndex, int userId, String subvariable, String isHomogenous, Boolean polarity,
            UsedCriterion usedCriterionMain) {

        Value value = new Value();
        if (!polarity) {
            answerIndex = Math.abs(6 - answerIndex);
        }
        value.setValue(answerIndex);

        ids.put(userEmail, userId);
        if (participantsMap.containsKey(userEmail)) {
            ArrayList<Criterion> criteria = participantsMap.get(userEmail);
            if (criteria.contains(usedCriterionMain)) {
                int i1 = criteria.indexOf(usedCriterionMain);
                Criterion criterion1 = criteria.get(i1);
                int size = criterion1.getValues().size();
                value.setName("value" + (size));
                criterion1.getValues().add(value);
                criteria.remove(criterion1);
                criteria.add(criterion1);
            } else {
                value.setName("value" + 0);
                usedCriterionMain.getValues().add(value);
                criteria.add(usedCriterionMain);
            }
        } else {
            Criterion criterion1 = new Criterion();
            criterion1.setName(subvariable);
            criterion1.setIsHomogeneous(isHomogenous);
            value.setName("value" + 0);
            criterion1.getValues().add(value);
            ArrayList<Criterion> criteria = new ArrayList<>();
            criteria.add(criterion1);
            participantsMap.put(userEmail, criteria);
        }
    }


    private void fillPojo(
            ArrayList<UsedCriterion> usedCriterions, ParticipantsHolder participantsHolder,
            HashMap<String, ArrayList<Criterion>> participantsMap, HashMap<String, Integer> ids) {
        // add user criteria
        participantsHolder.setVersion(1);
        UsedCriteria usedCriteria = new UsedCriteria();
        usedCriteria.setCriterions(usedCriterions);
        participantsHolder.setUsedCriteria(usedCriteria);

        // add participants
        for (String participantEmail : participantsMap.keySet()) {
            Participants participant = new Participants();
            participant.setId(ids.get(participantEmail));
            participant.setCriterion(participantsMap.get(participantEmail));
            participantsHolder.getParticipants().add(participant);
        }
    }


    private UsedCriterion fillUsedCriterion(
            ArrayList<UsedCriterion> usedCriterions, String subvariable, String isHomogenous) {
        UsedCriterion usedCriterionMain = new UsedCriterion();
        usedCriterionMain.setName(subvariable);
        usedCriterionMain.setIsHomogeneous(isHomogenous);
        usedCriterionMain.setMinValue(1f);
        usedCriterionMain.setMaxValue(5f);
        usedCriterionMain.setWeight(1f);
        usedCriterionMain.setValueCount(1);
        if (usedCriterions.contains(usedCriterionMain)) {
            UsedCriterion usedCriterion = usedCriterions.get(usedCriterions.indexOf(usedCriterionMain));
            usedCriterion.setValueCount(usedCriterion.getValueCount() + 1);
            usedCriterions.remove(usedCriterionMain);
            usedCriterions.add(usedCriterionMain);
        } else {
            usedCriterions.add(usedCriterionMain);
        }
        return usedCriterionMain;
    }

    public void createNewSurveyProject(Project project) {
        connect.connect();
        String query =
                "Insert into surveyitemsselected (projectname, profilequestionid) " + " SELECT ?, id FROM profilequestions";

        connect.issueInsertOrDeleteStatement(query, project.getName());
        connect.close();
    }

    public GroupWorkContext getGroupWorkContext(Project project) {
        connect.connect();
        String query = "SELECT * FROM projects WHERE name=?";
        VereinfachtesResultSet vereinfachtesResultSet = connect.issueSelectStatement(query, project.getName());
        GroupWorkContext groupWorkContext = GroupWorkContext.fl;
        if (vereinfachtesResultSet.next()) {
            groupWorkContext = GroupWorkContext.valueOf(vereinfachtesResultSet.getString("context"));
        }
        return groupWorkContext;
    }
}
