package unipotsdam.gf.core.database;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import unipotsdam.gf.modules.reflection.model.LearningGoalStoreItem;
import unipotsdam.gf.modules.reflection.model.ReflectionQuestionsStoreItem;
import unipotsdam.gf.modules.reflection.service.LearningGoalStoreDAO;
import unipotsdam.gf.modules.reflection.service.ReflectionQuestionsStoreDAO;
import unipotsdam.gf.mysql.MysqlConnect;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class PredefinedDataInsertionHelper {

    private MysqlConnect connection;

    public PredefinedDataInsertionHelper(MysqlConnect connection) {
        this.connection = connection;
    }

    public void saveLearningGoals(String fileName) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        InputStream inputStream = new FileInputStream(fileName);
        List<LearningGoalStoreItem> learningGoalStoreItems = objectMapper.readValue(inputStream, new TypeReference<List<LearningGoalStoreItem>>() {
        });
        LearningGoalStoreDAO learningGoalStoreDAO = new LearningGoalStoreDAO(connection);
        learningGoalStoreItems.forEach(learningGoalStoreDAO::persist);
    }

    public void saveReflecionQuestions(String fileName) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        InputStream inputStream = new FileInputStream(fileName);
        List<ReflectionQuestionsStoreItem> items = objectMapper.readValue(inputStream, new TypeReference<List<ReflectionQuestionsStoreItem>>() {
        });
        ReflectionQuestionsStoreDAO storeDAO = new ReflectionQuestionsStoreDAO(connection);
        items.forEach(storeDAO::persist);
    }


}
