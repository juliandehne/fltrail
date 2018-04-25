package uzuzjmd.competence.algorithms.similarity;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * Created by dehne on 13.09.2017.
 */
public class WordToStemPluginWrapper extends WordTransformationPlugin {
    public WordToStemPluginWrapper(Double weight) {
        super(weight);
    }

    @Override
    public int getReduction() {
        return MatchPriority.STEMMERREDUCTION;
    }

    @Override
    public List<String> getSimilarWords(String input) throws Exception {
        return Lists.newArrayList(WordToStemPlugin.stemWord(input));
    }
}
