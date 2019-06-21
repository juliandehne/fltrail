package unipotsdam.gf;

import java.util.HashMap;
import java.util.Map;

public class HashShower {
    public static void main(String[] args) {
        /*String toShow = "fl_HUL_2110@uni.de";
        Optional<String> reduce = toShow.chars()
                .mapToObj(value -> value + "-")
                .reduce((s, s2) -> s + s2);
        System.out.println(reduce.get().substring(0, reduce.get().length() - 1));
        */
        System.out.println("context: peerassessmenttest5, Hash: " + "peerassessmenttest5".hashCode());
/*
        GroupWorkContext[] values = GroupWorkContext.values();
        for (GroupWorkContext value : values) {
            System.out.println("context: " + value + ", Hash: "+ value.toString().hashCode());

        }
*/
    }

    ///just testing stuff
    public static void testMe() {
        Map<String, String> test = new HashMap<>();
        test.put("ich", "du");
        test.put("er", "du");
        test.put("wir", "du");
        Integer i = 0;
        for (String test1 : test.keySet()) {
            i++;
        }
    }

}
