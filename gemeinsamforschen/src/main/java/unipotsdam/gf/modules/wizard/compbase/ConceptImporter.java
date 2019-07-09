package unipotsdam.gf.modules.wizard.compbase;

import com.opencsv.CSVReader;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class ConceptImporter {

    private List<String> concepts = getConcepts();
    public int size = concepts.size();

    private static final String CONCEPT_FILE_FOR_WIZARD = "SentiWS_v2.0_Positive.csv";

    private List<String> getConcepts() {
        if (concepts == null) {
            java.util.List<java.util.List<String>> records = new ArrayList<>();

            Reader fileReader = null;
            if (isJUnitTest()) {
                String path = System.getProperty("user.dir") + "/src/main/resources/" + CONCEPT_FILE_FOR_WIZARD;
                try {
                    fileReader = new FileReader(path);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            } else {
                InputStream resourceAsStream = getClass().getClassLoader().getResourceAsStream(CONCEPT_FILE_FOR_WIZARD);
                fileReader = new InputStreamReader(resourceAsStream);
            }
            try (CSVReader csvReader = new CSVReader(fileReader)) {
                String[] values = null;
                while ((values = csvReader.readNext()) != null) {
                    records.add(Arrays.asList(values));
                }
            } catch (IOException e) {
                throw new Error("did not find the file:" + CONCEPT_FILE_FOR_WIZARD);
            } return records.stream().map(x -> x.get(0)).collect(Collectors.toList());
        } else {
            return concepts;
        }
    }

    public List<String> getNumberedConcepts(int number) {
        Random random = new Random();
        int startIndex = random.nextInt(size - number);
        return concepts.subList(startIndex, startIndex + number);
    }

    protected boolean isJUnitTest() {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        List<StackTraceElement> list = Arrays.asList(stackTrace);
        for (StackTraceElement element : list) {
            if (element.getClassName().startsWith("org.junit.")) {
                return true;
            }
        }
        return false;
    }
}
