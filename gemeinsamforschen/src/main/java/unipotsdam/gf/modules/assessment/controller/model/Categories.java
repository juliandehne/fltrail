package unipotsdam.gf.modules.assessment.controller.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Categories {
    /**
     * the categories are good, but not used at the moment
     */
  /*  public static final List<String> workRatingCategories = Collections.unmodifiableList(
            new ArrayList<String>() {{
                add("responsibility");
                add("partOfWork");
                add("cooperation");
                add("communication");
                add("autonomous");
            }}
    );*/

    public static final List<String> standardAnnotationCategories = Collections.unmodifiableList(
            new ArrayList<String>() {{
                add(EnumCategories.RECHERCHE.toString());
                add(EnumCategories.LITERATURVERZEICHNIS.toString());
                add(EnumCategories.FORSCHUNGSFRAGE.toString());
                add(EnumCategories.UNTERSUCHUNGSKONZEPT.toString());
                add(EnumCategories.METHODIK.toString());
                add(EnumCategories.DURCHFUEHRUNG.toString());
                add(EnumCategories.AUSWERTUNG.toString());

            }}
            /*
            new ArrayList<String>() {{
        add("Erkenntnisinteresse");
        add("Forschungsfrage");
        add("Forschungsstand");
        add("Theoretische Gegenstandsbestimmung");
        add("Erhebungsmethode");
        add("Auswertungsmethode");
        add("Sample");
        add("Zugang zum Feld");
        add("Erhebungsinstrument");
        add("Zeitplan");
        add("Literatur");
        add("Anhang");
    }}*/
    );
}