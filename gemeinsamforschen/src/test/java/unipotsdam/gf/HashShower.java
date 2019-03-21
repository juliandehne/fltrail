package unipotsdam.gf;

import unipotsdam.gf.modules.group.preferences.survey.GroupWorkContext;

import java.util.Optional;

public class HashShower {
    public static void main(String[] args) {
        /*String toShow = "fl_HUL_2110@uni.de";
        Optional<String> reduce = toShow.chars()
                .mapToObj(value -> value + "-")
                .reduce((s, s2) -> s + s2);
        System.out.println(reduce.get().substring(0, reduce.get().length() - 1));
        */

        GroupWorkContext[] values = GroupWorkContext.values();
        for (GroupWorkContext value : values) {
            System.out.println("context: " + value + ", Hash: "+ value.toString().hashCode());

        }
    }


}
