package uzuzjmd.competence.algorithms.similarity

import config.MagicStrings
import mysql.MysqlConnect

import scala.collection.JavaConverters._

/**
 * @author dehne
  *        creates synonyms based on the openthesaurs library
 */
object OpenThesaurusSynonymPlugin {
  def getSynonyms(input: String): List[String] = {

     val connector = new MysqlConnect
    connector.connect(createConnectionString)

    var result = List(): List[String]

    val resultSet = connector.issueSelectStatement("" +
      "SELECT b.word FROM term a INNER JOIN term b on a.synset_id = b" +
      ".synset_id where a.word like ? LIMIT 30 ", input)

    
    while (resultSet.next()) {
        val currentWord = resultSet.getString("word")
        result = currentWord  :: result
    }

    connector.close()
    return result
  }

  def getSynonymsAsJava(input: String) : java.util.List[String] = {
    getSynonyms(input).asJava
  }

  private def createConnectionString: String = {
    return "jdbc:mysql://" + MagicStrings.thesaurusDatabaseUrl +
      "/" + MagicStrings.thesaurusDatabaseName +
      "?user=" + MagicStrings.thesaurusLogin +
      "&password=" + MagicStrings.thesaurusPassword;
  }
}