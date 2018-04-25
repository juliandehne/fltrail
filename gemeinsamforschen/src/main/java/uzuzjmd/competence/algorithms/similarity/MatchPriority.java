package uzuzjmd.competence.algorithms.similarity;

/**
 * Created by dehne on 11.05.2017.
 */
public class MatchPriority {
    // priority 11

    public static Double BASE = Math.sqrt(2d);
    public static Double STEP = 1d;
    private static Double USER_SIMILARITY_PRIORITY = 10d;
    private static Double USER_CATCHWORD_PRIORITY = USER_SIMILARITY_PRIORITY - STEP;
    private static Double PARSED_CATCHWORD_PRIORITY = USER_CATCHWORD_PRIORITY - STEP ;

    public static Double USER_SIMILARITY_WEIGHT  = Math.pow(BASE,USER_SIMILARITY_PRIORITY);
    public static Double USER_CATCHWORD_WEIGHT  = Math.pow(BASE,USER_CATCHWORD_PRIORITY);
    public static Double PARSED_CATCHWORD_WEIGHT  = Math.pow(BASE,PARSED_CATCHWORD_PRIORITY);

    public static int LEVENSHTEIN1REDUCTION = 3;
    public static int LEVENSHTEIN2REDUCTION = 4;
    public static int GERMANETSYNREDUCTION = 1;
    public static int THESAURUSSYNREDUCTION = 7;
    public static int GERMANETSIMREDUCTION = 2;
    public static int STEMMERREDUCTION = 5;
    public static int COLOGNEREDUCTION = 6;


}
