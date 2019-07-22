package unipotsdam.gf.modules.evaluation;

import unipotsdam.gf.modules.project.Project;
import unipotsdam.gf.modules.project.ProjectDAO;
import unipotsdam.gf.modules.user.User;
import unipotsdam.gf.modules.user.UserDAO;
import unipotsdam.gf.mysql.MysqlConnect;

import javax.inject.Inject;
import java.util.HashMap;

public class EvaluationDAO {

    @Inject
    MysqlConnect connect;

    @Inject
    ProjectDAO projectDAO;

    @Inject
    UserDAO userDAO;

    private final EvaluationsQuestions evaluationsQuestions;

    public EvaluationDAO() {
        this.evaluationsQuestions = new EvaluationsQuestions();
    }

    public void addSUSData(User user, Project project, HashMap<String, String> data) throws Exception {
        Boolean isDocent = projectDAO.getProjectByName(project.getName()).getAuthorEmail().equals(user.getEmail());
        connect.connect();
        String query =
                "INSERT INTO evaluationsus (`project`, `user`, `questionId`, `rating`, `docent`)"
                        + " values (?,?,?,?,?)";
        for (String s : data.keySet()) {
            int ratingNotPolarized = Integer.parseInt(data.get(s));
            int ratingPolarized = ratingNotPolarized;
            if (evaluationsQuestions.getPolarityMap().get(s)) {
                ratingPolarized = 6 - ratingNotPolarized;
            }
            connect.issueInsertOrDeleteStatement(query, project.getName(), user.getEmail(), s, ratingPolarized,
                    isDocent);
        }
        connect.close();
    }
}
