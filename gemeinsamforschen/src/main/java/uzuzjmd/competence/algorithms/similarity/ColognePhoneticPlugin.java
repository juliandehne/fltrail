package uzuzjmd.competence.algorithms.similarity;

import org.apache.commons.codec.language.ColognePhonetic;
import uzuzjmd.competence.persistence.SqlDictionary;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 * Created by dehne on 12.09.2017.
 */
public class ColognePhoneticPlugin extends WordTransformationPlugin {

    public ColognePhoneticPlugin(Double weight) {
        super(weight);
    }

    @Override
    public int getReduction() {
        return MatchPriority.COLOGNEREDUCTION;
    }

    /**
     * get the cologne phonetics words contained in the list
     *
     * @param query
     * @param catchwords
     * @return
     * @throws Exception
     */
    public static List<String> createColognePhoneticSynsets(String query, List<String> catchwords) throws Exception {
        if (catchwords.isEmpty()) {
            return new ArrayList<>();
        }
        ArrayList<String> result = new ArrayList<>();
        String[] dictionary2 = SqlDictionary.createDictionary(catchwords).toArray(new String[0]);

        ColognePhonetic phonetics = new ColognePhonetic();
        String queryPhonetics = phonetics.colognePhonetic(query);
        ArrayList<String> phoneticsRepresentation = new ArrayList<String>();
        HashMap<String, String> phoneticsOrigins = new HashMap<String,String>();
        for (String s : dictionary2) {
            String sPhonetics = phonetics.colognePhonetic(s);
            phoneticsRepresentation.add(sPhonetics);
            phoneticsOrigins.put(sPhonetics, s);
        }
        if (phoneticsRepresentation.contains(queryPhonetics)) {
            result.add(phoneticsOrigins.get(queryPhonetics));
        }
        return result;
    }

    @Override
    public List<String> getSimilarWords(String query) throws
            Exception {
        Set<String> catchwords = SqlDictionary.getCatchwords();
        return createColognePhoneticSynsets(query, new ArrayList<>(catchwords));
    }


}
