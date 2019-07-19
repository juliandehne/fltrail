package unipotsdam.gf.modules.annotation.controller;

import unipotsdam.gf.interfaces.IAnnotation;
import unipotsdam.gf.modules.annotation.model.*;
import unipotsdam.gf.modules.assessment.controller.model.Categories;
import unipotsdam.gf.modules.project.Project;
import unipotsdam.gf.mysql.MysqlConnect;
import unipotsdam.gf.mysql.VereinfachtesResultSet;
import unipotsdam.gf.process.tasks.Task;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class AnnotationController implements IAnnotation {

    @Inject
    MysqlConnect connection;

    @Override
    public Annotation addAnnotation(AnnotationPostRequest annotationPostRequest) {

        // create a new id if we found no id.
        String uuid = UUID.randomUUID().toString();
        while (existsAnnotationId(uuid)) {
            uuid = UUID.randomUUID().toString();
        }

        // establish connection
        connection.connect();

        // build and execute request
        String request = "INSERT INTO annotations (`id`, `userEmail`, `targetId`, `targetCategory`, `title`, `comment`, `startCharacter`, `endCharacter`) VALUES (?,?,?,?,?,?,?,?);";
        connection.issueInsertOrDeleteStatement(request, uuid, annotationPostRequest.getUserEmail(),
                annotationPostRequest.getTargetId(), annotationPostRequest.getTargetCategory().toString().toUpperCase(), annotationPostRequest.getBody().getTitle(), annotationPostRequest.getBody().getComment(), annotationPostRequest.getBody().getStartCharacter(), annotationPostRequest.getBody().getEndCharacter());

        // close connection
        connection.close();

        // build response annotation
        return getAnnotation(uuid);

    }

    @Override
    public void alterAnnotation(String annotationId, AnnotationPatchRequest annotationPatchRequest) {

        // establish connection
        connection.connect();

        // build and execute request
        String request = "UPDATE `annotations` SET `title` = ?, `comment` = ? WHERE `id` = ?";
        connection.issueUpdateStatement(request, annotationPatchRequest.getTitle(), annotationPatchRequest.getComment(), annotationId);

        // close connection
        connection.close();

    }

    @Override
    public void deleteAnnotation(String annotationId) {

        // establish connection

        connection.connect();

        // build and execute request
        String request = "DELETE FROM annotations WHERE id = ?;";
        connection.issueInsertOrDeleteStatement(request, annotationId);

        // close connection
        connection.close();

    }

    @Override
    public Annotation getAnnotation(String annotationId) {

        // establish connection

        connection.connect();

        // build and execute request
        String request = "SELECT * FROM annotations WHERE id = ?;";
        VereinfachtesResultSet rs = connection.issueSelectStatement(request, annotationId);

        if (rs.next()) {

             // save annotation
            Annotation annotation = getAnnotationFromResultSet(rs);

            // close connection
            connection.close();

            return annotation;
        }
        else {
            // close connection
            connection.close();
            return null;
        }
    }

    public String getFinishedDossier(Project project, Integer groupId){
        connection.connect();

        // build and execute request
        String request = "SELECT * FROM fullsubmissions fs JOIN groupuser gu ON gu.userEmail=fs.user " +
                "AND gu.groupId=? WHERE projectname = ? AND finalized=1;";
        VereinfachtesResultSet rs = connection.issueSelectStatement(request, groupId, project.getName());

        if (rs!= null && rs.next()) {
            return rs.getString("text");
        }else{
            return "random shit";
        }
    }

    @Override
    public ArrayList<Annotation> getAnnotations(String targetId, Category category) {

        // declare annotation ArrayList
        ArrayList<Annotation> annotations = new ArrayList<>();

        // establish connection

        connection.connect();

        // build and execute request
        String request = "SELECT * FROM annotations WHERE targetId = ? AND targetCategory = ?;";
        VereinfachtesResultSet rs = connection.issueSelectStatement(request, targetId, category.toString().toUpperCase());

        while (rs.next()) {
            annotations.add(getAnnotationFromResultSet(rs));
        }

        // close connection
        connection.close();

        return annotations;

    }

    @Override
    public boolean existsAnnotationId(String annotationId) {

        // establish connection

        connection.connect();

        // build and execute request
        String request = "SELECT COUNT(*) > 0 AS `exists` FROM annotations WHERE id = ?;";
        VereinfachtesResultSet rs = connection.issueSelectStatement(request, annotationId);

        if (rs.next()) {
            // save the response
            int count = rs.getInt("exists");

            // close connection
            connection.close();

            // return true if we found the id
            if (count < 1) {
                return false;
            }
            else {
                return true;
            }
        }

        // something happened
        return true;

    }

    public void endFeedback(Task task){
        connection.connect();
        String query = "UPDATE tasks set progress = ? where groupTask = ? AND projectName = ? AND taskName = ?";
        connection.issueUpdateStatement(
                query, task.getProgress().name(), task.getGroupTask(), task.getProjectName(), task.getTaskName());
        connection.close();
    }

    /**
     * Build an annotation object from a given result set
     *
     * @param rs The result set from a database query
     * @return A new annotation object
     */
    private Annotation getAnnotationFromResultSet(VereinfachtesResultSet rs) {

        String id = rs.getString("id");
        long timestamp = rs.getTimestamp(2).getTime();
        String userEmail = rs.getString("userEmail");
        String targetId = rs.getString("targetId");
        Category targetCategory = Category.valueOf(rs.getString("targetCategory"));

        // initialize new annotation body
        String title = rs.getString("title");
        String comment = rs.getString("comment");
        int startCharacter = rs.getInt("startCharacter");
        int endCharacter = rs.getInt("endCharacter");
        AnnotationBody body = new AnnotationBody(title, comment, startCharacter, endCharacter);

        return new Annotation(id, timestamp, userEmail, targetId, targetCategory, body);
    }

    public void setAnnotationCategories(Project project) {
        List<String> categories;
        if (project.getCategories().size() == 0) {
            categories = Categories.standardAnnotationCategories;
        } else {
            categories = project.getCategories();
        }
        connection.connect();
        for (String category : categories) {
            String query = "INSERT INTO `categoriesselected`(`projectName`, `categorySelected`) VALUES (?,?)";
            connection.issueUpdateStatement(
                    query, project.getName(), category);
        }
        connection.close();
    }
}
