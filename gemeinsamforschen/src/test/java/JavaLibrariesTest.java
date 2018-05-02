import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;
import org.junit.Test;

import java.util.Arrays;
import java.util.Random;

/**
 * Created by dehne on 02.05.2018.
 */
public class JavaLibrariesTest {


    /**
     * Java Math ist nützlich...
     */
    @Test
    public void randomMathStuff() {
        Random random = new Random();
        int randomInt = random.nextInt(300); // get a random int between 0 and 300
        System.out.println(randomInt);
    }

    /**
     * Guava ist mächtig, wenn man besondere Datenstrukturen braucht
     */
    @Test
    public void someCollectionStuff() {
        final President[] SOME_PRESIDENTS_IN_ORDER = new President[]{
                new President("Nelson", "Mandela"),
                new President("Ciang", "ciong")};

        ListMultimap<String, String> multimap = ArrayListMultimap.create();
        for (President pres : SOME_PRESIDENTS_IN_ORDER) {
            multimap.put(pres.getFirstName(), pres.getLastName());
        }
        for (String firstName : multimap.keySet()) {
            java.util.List<String> lastNames = multimap.get(firstName);
            System.out.println(firstName + ": " + lastNames);
        }
    }

    private class President {
        public President(String firstName, String lastName) {
            this.firstName = firstName;
            this.lastName = lastName;
        }

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        private String firstName;



        private String lastName;

    }
}
