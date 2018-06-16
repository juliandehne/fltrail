package unipotsdam.gf.modules.annotation.controller;

import unipotsdam.gf.core.database.mysql.MysqlConnect;
import unipotsdam.gf.core.database.mysql.VereinfachtesResultSet;
import unipotsdam.gf.interfaces.IAnnotation;
import unipotsdam.gf.modules.annotation.model.Annotation;
import unipotsdam.gf.modules.annotation.model.AnnotationPatchRequest;
import unipotsdam.gf.modules.annotation.model.AnnotationPostRequest;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.UUID;

public class AnnotationController implements IAnnotation {
    @Override
    public Annotation addAnnotation(AnnotationPostRequest annotationPostRequest) {

        // create a new id if we found no id.
        String uuid = UUID.randomUUID().toString();
        while (existsAnnotationId(uuid)) {
            uuid = UUID.randomUUID().toString();
        }

        // build the annotation
        Annotation annotation = new Annotation(uuid,
                ZonedDateTime.now().toEpochSecond(),
                annotationPostRequest.getUserId(),
                annotationPostRequest.getTargetId(),
                annotationPostRequest.getBody(),
                annotationPostRequest.getStartCharacter(),
                annotationPostRequest.getEndCharacter());

        // establish connection
        MysqlConnect connection = new MysqlConnect();
        connection.connect();

        // build and execute request
        String request = "INSERT INTO annotations (`id`, `userId`, `targetId`, `body`, `startCharacter`, `endCharacter`) VALUES (?,?,?,?,?,?);";
        connection.issueInsertOrDeleteStatement(request, annotation.getId(), annotation.getUserId(), annotation.getTargetId(), annotation.getBody(), annotation.getStartCharacter(), annotation.getEndCharacter());

        // close connection
        connection.close();

        return annotation;

    }

    @Override
    public void alterAnnotation(String annotationId, AnnotationPatchRequest annotationPatchRequest) {

        // establish connection
        MysqlConnect connection = new MysqlConnect();
        connection.connect();

        // build and execute request
        String request = "UPDATE `annotations` SET `body` = ? WHERE `id` = ?";
        connection.issueUpdateStatement(request, annotationPatchRequest.getBody(), annotationId);

        // close connection
        connection.close();

    }

    @Override
    public void deleteAnnotation(String annotationId) {

        // establish connection
        MysqlConnect connection = new MysqlConnect();
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
        MysqlConnect connection = new MysqlConnect();
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

    @Override
    public ArrayList<Annotation> getAnnotations(int targetId) {

        // declare annotation ArrayList
        ArrayList<Annotation> annotations = new ArrayList<>();

        // establish connection
        MysqlConnect connection = new MysqlConnect();
        connection.connect();

        // build and execute request
        String request = "SELECT * FROM annotations WHERE targetId = ?;";
        VereinfachtesResultSet rs = connection.issueSelectStatement(request, targetId);

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
        MysqlConnect connection = new MysqlConnect();
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

    private Annotation getAnnotationFromResultSet(VereinfachtesResultSet rs) {

        String id = rs.getString("id");
        long timestamp = rs.getTimestamp(2).getTime();
        int userId = rs.getInt("userId");
        int targetId = rs.getInt("targetId");
        String body = rs.getString("body");
        int startCharacter = rs.getInt("startCharacter");
        int endCharacter = rs.getInt("endCharacter");

        return new Annotation(id, timestamp, userId, targetId, body, startCharacter, endCharacter);

    }
}
