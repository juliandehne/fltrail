package uzuzjmd.competence.algorithms.similarity;

import com.github.liblevenshtein.transducer.Algorithm;
import com.github.liblevenshtein.transducer.Candidate;
import com.github.liblevenshtein.transducer.ITransducer;
import com.github.liblevenshtein.transducer.factory.TransducerBuilder;
import uzuzjmd.competence.persistence.SqlDictionary;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by dehne on 15.06.2017.
 */
public class LevensteinsPlugin extends WordTransformationPlugin {

    public LevensteinsPlugin(Double weight) {
        super(weight);
    }

    @Override
    public int getReduction() {
        return MatchPriority.LEVENSHTEIN1REDUCTION;
    }

    /**
     * get the levenshteins words with a certain distance given the dictionary
     * @param catchwords
     * @param queryTerm
     * @param distance
     * @return
     * @throws Exception
     */
    public static ArrayList<String> createLevenshteinsSynsets(Set<String> catchwords, String queryTerm, int distance)
            throws
            Exception {
        final ITransducer<Candidate> transducer = new TransducerBuilder()
                .dictionary(SqlDictionary.createDictionary(new ArrayList<String>(catchwords)))
                .algorithm(Algorithm.TRANSPOSITION)
                .defaultMaxDistance(distance)
                .includeDistance(true)
                .build();

        ArrayList<String> results = new ArrayList<String>();
        for (final Candidate candidate : transducer.transduce(queryTerm)) {
            results.add(candidate.term());
        }

        return results;
    }



    public static ArrayList<String> createLevenshteinsSynsets(String queryTerm, int distance) throws Exception {
        Set allInstanceDefinitions = SqlDictionary.getCatchwords();
        return createLevenshteinsSynsets(allInstanceDefinitions, queryTerm, distance);
    }


    @Override
    public List<String> getSimilarWords(String input) throws Exception {
        return createLevenshteinsSynsets(input, 1);
    }

    public List<String> getSimilarWords2(String input) throws Exception {
        return createLevenshteinsSynsets(input, 2);
    }
}
