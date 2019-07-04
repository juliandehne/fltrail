package unipotsdam.gf.modules.wizard.compbase;

import com.opencsv.CSVReader;
import unipotsdam.gf.config.CompbaseConfig;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class ConceptImporter {

    public static List<String> concepts = getConcepts();
    public static int size = concepts.size();

    private static List<String> getConcepts() {
        if (concepts == null) {
            java.util.List<java.util.List<String>> records = new ArrayList<>();
            try (CSVReader csvReader = new CSVReader(new FileReader(CompbaseConfig.CONCEPT_FILE_FOR_WIZARD));) {
                String[] values = null;
                while ((values = csvReader.readNext()) != null) {
                    records.add(Arrays.asList(values));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return records.stream().map(x->x.get(0)).collect(Collectors.toList());
        } else {
            return concepts;
        }
    }

    public static List<String> getNumberedConcepts(int number) {
        Random random = new Random();
        int startIndex = random.nextInt(size - number);
        return concepts.subList(startIndex, startIndex + number);
    }
}
