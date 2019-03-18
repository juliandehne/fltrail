package unipotsdam.gf;

import java.util.Optional;

public class HashShower {
    public static void main(String[] args) {
        String toShow = "dota_12@uni.de";
        Optional<String> reduce = toShow.chars()
                .mapToObj(value -> value + "-")
                .reduce((s, s2) -> s + s2);
        System.out.println(reduce.get().substring(0, reduce.get().length() - 1));
    }
}
