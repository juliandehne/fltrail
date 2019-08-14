package unipotsdam.gf.modules.submission.model;

import java.util.Arrays;
import java.util.HashMap;

public enum Visibility {

    PUBLIC("Öffentlich sichtbar"),
    DOCENT("Für die dozierende Person sichtbar"),
    GROUP("Für die Gruppe sichtbar"),
    PERSONAL("Nur für Sie sichtbar");

    private String buttonText;

    Visibility(String buttonText) {
        this.buttonText = buttonText;
    }

    public static HashMap<Visibility, String> toVisibilityButtonTextMap() {
        HashMap<Visibility, String> visibilityButtonTextHashMap = new HashMap<>();

        Arrays.stream(Visibility.values()).forEach(visibility -> {
            visibilityButtonTextHashMap.put(visibility, visibility.buttonText);
        });

        return visibilityButtonTextHashMap;
    }
}
