package uzuzjmd.competence.algorithms.similarity;

import java.util.List;

/**
 * Created by dehne on 11.10.2017.
 */
public interface IWordTransformationPlugin {
    /**
     * Takes as a second input a dictionary (based on the existing database)
     * @param input
     * @return
     * @throws Exception
     */
    List<String> getSimilarWords(String input) throws Exception;
}
