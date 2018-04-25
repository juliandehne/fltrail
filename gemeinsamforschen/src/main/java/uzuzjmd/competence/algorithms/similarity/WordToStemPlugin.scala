package uzuzjmd.competence.algorithms.similarity

import org.tartarus.snowball.ext.germanStemmer
;

/**
 * @author dehne
 */
object WordToStemPlugin {

  val stemmer = new germanStemmer();
  def stemWord(input: String): String = {

    //val stemClass = Class.forName("org.tartarus.snowball.ext." + "german" + "Stemmer");
    //val stemmer = stemClass.newInstance().asInstanceOf[germanStemmer];

    stemmer.setCurrent(input);
    stemmer.stem();
    val result = stemmer.getCurrent();
    return result.substring(0, result.length()-1)
  }


  def getStemmedWordList(input: String): List[String] = {
    return (stemWord(input)):: Nil
  }
}