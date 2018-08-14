package unipotsdam.gf.modules.assessment.controller.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Categories {
    public static final List<String> workRatingCategories = Collections.unmodifiableList(
            new ArrayList<String>() {{
                add("responsibility");
                add("partOfWork");
                add("cooperation");
                add("communication");
                add("autonomous");
            }}
    );
    public static final List<String> contributionRatingCategories = Collections.unmodifiableList(
            new ArrayList<String>() {{
                add("Dossier");
                add("eJournal");
                add("research");
            }}
    );
}