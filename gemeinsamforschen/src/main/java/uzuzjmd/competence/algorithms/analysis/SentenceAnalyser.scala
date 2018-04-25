package uzuzjmd.competence.algorithms.analysis

import java.util
import edu.stanford.nlp.ling.Sentence
import edu.stanford.nlp.trees.Tree
import uzuzjmd.competence.algorithms.similarity.WordToStemPlugin
import uzuzjmd.competence.logging.Logging
import scala.collection.JavaConverters._

/**
  * @author dehne
  *         SubObjects of this trait are filter to get a specific word class out of a sentence.
  */
trait SentenceAnalyser extends Logging {


  def hitList = List("VVIZU", "VVINF", "VVFIN", "VVPP", "ADJD") // as an example all verbs

  /**
    *
    */
  def convertSentenceToFilteredElement(input: String): List[String] = {

    if (input == null) {
      return List.empty[String]
    }

    if (input.size < 20 || !input.contains(" ") || input.trim.length == 0) {
      return List.empty[String]
    }

    //SystemOutMuter.restartSystemOut();
    //System.out.println("parsing input:" + input);

    //val tlp = new PennTreebankLanguagePack();
    // Uncomment the following line to obtain original Stanford Dependencies
    // tlp.setGenerateOriginalDependencies(true);
    //    val gsf = tlp.grammaticalStructureFactory();
    val sent = input.split(" ").toList.map(x=>x.trim).filterNot(_.length < 1).filterNot(_.length > 20)
    if (sent.size < 2) {
      return List.empty
    }

    val parse = ParserFactory.instance().apply(Sentence.toWordList(sent.asJava));
    //val parse =  ParserFactory.getLP().apply(Sentence.toWordList(sent.asJava));
    //    val gs = gsf.newGrammaticalStructure(parse);
    //    val tdl = gs.typedDependenciesCCprocessed();
    //    println(tdl.toString())
    val result = treeWalker(parse)

    // remove subjects
    return result

  }

  def convertSentenceToFilteredElementJava(input: String): java.util.List[String] = {
    val result = convertSentenceToFilteredElement(input.replaceAll("NN", "N"))
    return result.asJava
  }

  def convertSentenceToFilteredElementStemmedJava(input: String): java.util.List[String] = {
    val result: util.List[String] = convertSentenceToFilteredElementStemmed(input).asJava
    return result
  }

  def convertSentenceToFilteredElementStemmed(input: String): List[String] = {
    return convertSentenceToFilteredElement(input).map { x => WordToStemPlugin.stemWord(x) }
  }


  /**
    * recursively walks the dependency tree in order to find all the words given the specified filter
    */
  def treeWalker(input: Tree): List[String] = {
    val startList = List(): List[String]
    return treeWalker_Helper(input, startList)
  }


  def treeWalker_Helper(input: Tree, startList: List[String]): List[String] = {
    if (hitList.forall(x => !input.nodeString().contains(x))) {
      return startList ::: input.children().flatMap(x => treeWalker_Helper(x, startList)).toList
    } else {
      if (input.size() == 0) {
        return List.empty
      }
      val newList = input.getChild(0).toString :: startList
      return startList ::: input.children().flatMap(x => treeWalker_Helper(x, newList)).toList
    }

  }
}