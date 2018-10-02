package unipotsdam.gf.testsandbox;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;
import unipotsdam.gf.modules.project.Project;

public class JacksonPojoToJson {


    /**
     * Utility to creaty dummy data for students
     */
    static PodamFactory factory = new PodamFactoryImpl();

    public static void main(String[] args) throws Exception {

        // shows how to use
        writeExample(Project.class);
    }

    public static void writeExample(Class object) throws JsonProcessingException {
        // Create ObjectMapper
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);

        Object mappable = factory.manufacturePojo(object);

        // Convert object to JSON string
        String json = mapper.writeValueAsString(mappable);
        System.out.println(json);
    }
}

