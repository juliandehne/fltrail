package uzuzjmd.competence.algorithms.similarity

import java.{lang, util}

import uzuzjmd.competence.logging.Logging
import uzuzjmd.competence.persistence.SQLSimilarityAlgorithmStorage
import uzuzjmd.competence.util.LanguageConverter

import scala.collection.JavaConverters._
import scala.collection.mutable

/**
  * Created by dehne on 04.10.2017.
  */
object SuggestionAlgorithm extends Logging with LanguageConverter{
  def getSuggestedSimilarCompetencesS1(competenceId: String):
  List[(String, lang.Double)] = {
    val manager = new SQLSimilarityAlgorithmStorage
    val input = competenceId;
    val result: util.ArrayList[util.ArrayList[AnyRef]] = manager.getSimilarNodesResultSet(input)
    if (result != null) {
      val resultAsScala = result.asScala
      val resultAsScala2 = resultAsScala.map(x => x.asScala)
      val resultFiltered2 = resultAsScala2.filterNot(x => x.head.equals(input))
      // using multiplication of sides in order to archieve max proximity
      val resultCalculated: mutable.Buffer[(String, Double)] =
        resultFiltered2
          .map(x =>
            (x.head.toString, x.drop(1).head.asInstanceOf[Double] * Math.abs(x.drop(3).head.asInstanceOf[Double])))
      val groupedResult = resultCalculated.groupBy(x => x._1)
      val groupedCleanedResult = groupedResult.map(x => (x._1, x._2.map(y => y._2).reduce((a, b) => (a + b))))
      val orderResult: List[(String, Double)] = groupedCleanedResult.toList
        .filter(x => x._2 > 0)
        .sortWith((a, b) => a._2 > b._2);
      return orderResult.map(x => (x._1, x._2.asInstanceOf[java.lang.Double]));
    } else {
      logger.warn("no similar learning goals found for: " + competenceId);
      return List.empty;
    }
  }

  /*def processSuggestedSimilarCompetencesS1(input: String) {
    val manager = new CompetenceNeo4JQueryManager
    val suggestions: List[(String, lang.Double)] = getSuggestedSimilarCompetencesS1(input)
    suggestions.foreach(x=>manager.createRelationShipWithWeight(input, Edge.SimilarTo, x._1, x._2, classOf[Competence],
      classOf[Competence]))
  }*/

  def getSimilarCompetencesRanked(baseline: String, acceptedAnswers: java.util.List[String]): Array[String] = {
    val it: List[(String, lang.Double)] = getSuggestedSimilarCompetencesS1(baseline)
    if (acceptedAnswers.isEmpty) {
      return it.map(x => x._1).take(5).toArray
    }
    val result: Array[String] = it.map(x => x._1).filter(acceptedAnswers.contains(_)).take(5).toArray
    return result
  }
}
