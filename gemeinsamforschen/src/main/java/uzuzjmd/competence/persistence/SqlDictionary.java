package uzuzjmd.competence.persistence;

import com.github.liblevenshtein.collection.dictionary.SortedDawg;
import com.github.liblevenshtein.serialization.PlainTextSerializer;
import com.github.liblevenshtein.serialization.Serializer;

import java.io.ByteArrayInputStream;
import java.util.HashSet;
import java.util.List;

/**
 * Created by dehne on 12.09.2017.
 */
public class SqlDictionary implements DictionaryStorage {


    public static HashSet<String> getCatchwords() {
       /* CompetenceNeo4JQueryManager competenceNeo4JQueryManager = new CompetenceNeo4JQueryManager();
        try {
            return new HashSet<>(competenceNeo4JQueryManager.getAllInstanceDefinitions(Label.SimilarityNode));
        } catch (Exception e) {
            e.printStackTrace();
        }*/
        return null;
    }

    public static SortedDawg createDictionary(List<String> catchwords) throws Exception {
        SortedDawg dictionary2 = getSortedDictionary(catchwords);
        return dictionary2;
    }

    public static  HashSet<String> getCompetences() {
        /*CompetenceNeo4JQueryManager competenceNeo4JQueryManager = new CompetenceNeo4JQueryManager();
        try {
            List<String> allInstanceDefinitions =
                    competenceNeo4JQueryManager.getAllInstanceDefinitions(Label.Competence);
            return new HashSet<>(allInstanceDefinitions);

        } catch (Exception e) {
            e.printStackTrace();
        }*/
        return new HashSet<>();
    }


    private static SortedDawg getSortedDictionary(List<String> allInstanceDefinitions) throws Exception {
        final Serializer serializer = new PlainTextSerializer(false);
        StringBuilder sb = new StringBuilder();
        for(String s : allInstanceDefinitions){
            sb.append(s);
            sb.append(System.getProperty("line.separator"));
        }
        ByteArrayInputStream stream = new ByteArrayInputStream( sb.toString().getBytes("UTF-8") );
        return (serializer.deserialize(SortedDawg.class, stream));
    }

}
