package uzuzjmd.competence.algorithms.similarity;

import uzuzjmd.competence.algorithms.analysis.SentenceToNoun;

/**
 * Created by dehne on 12.09.2017.
 */
public class SimilarityProcessor {


    public static void processCatchwordsGeneration(
           java.util.List<String> catchwordList, String competenceString) {

        String capitalization = competenceString;
        java.util.List<String> stringList = generateCatchwordsFromSentence(capitalization);
        TransformationRoutes.transform(stringList, competenceString, MatchPriority.PARSED_CATCHWORD_WEIGHT);
        TransformationRoutes.transform(catchwordList, competenceString, MatchPriority.USER_CATCHWORD_WEIGHT);
    }



    public static java.util.List<String> generateCatchwordsFromSentence(String definition) {
        return SentenceToNoun.convertSentenceToFilteredElementJava(definition);
    }


  /*  public static void processSimilarityEdges(String competenceId) {
        SuggestionAlgorithm.processSuggestedSimilarCompetencesS1(competenceId);
    }*/
}
