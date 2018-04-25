package uzuzjmd.competence.algorithms.analysis;

import config.MagicStrings;
import edu.stanford.nlp.parser.lexparser.LexicalizedParser;

/**
 * Created by dehne on 08.06.2017.
 */
public class ParserFactory {
    public static String GERMANMODELLOCATION2 = MagicStrings.GERMANMODELLOCATION2;

    public static final LexicalizedParser lp = instance();

    public static synchronized LexicalizedParser instance() {
        if (lp == null) {
            return LexicalizedParser.loadModel(GERMANMODELLOCATION2, "-maxLength", "200");
        } else {
            return lp;
        }
    }


}
