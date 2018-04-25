package uzuzjmd.competence.algorithms.similarity

import java.lang

import config.MagicStrings

import scala.collection.JavaConverters._
import PluginFactory._

/**
  * Created by dehne on 15.09.2017.
  */
object TransformationRoutes extends  DefaultInstrumented  {

  type PLUGIN_RESULT = (List[String], lang.Double, String)
  type MPF = PLUGIN_RESULT => PLUGIN_RESULT

  def transform(startCatchword: java.util.List[String], competence: String, startPriority: lang.Double)
  = {
    val init = (startCatchword.asScala.toList, startPriority, competence)
    // ########## routes

    /*val route1 = db _ :: germaNetSYN _ :: db _ :: Stemmer _ :: db _ :: Nil;
    val route2 = germaNetSim _ :: db _ :: Stemmer _ :: db _ ::  Nil;
    val route3 = thesaurus _ :: db _ ::  Stemmer _ :: db _ :: Nil*/
    val dbRoute = Validator _ :: db _ :: Nil
    val germaNetroute1 = Validator _ :: germaNetSYN _ :: Validator _  :: db _ :: Nil
    val germaNetroute2 = Validator _ :: germaNetSim _ :: Validator _ :: db _ ::  Nil
    val thesaurusRoute = thesaurus _ :: db _ :: Nil
    val stemmerRoute = Validator _ :: Stemmer _ :: Validator _ :: db _ ::  Nil
    val colognePhonetics = Validator _ :: cologne _ :: Validator _ :: db _ :: Nil
    val levenshteinRoute = Validator _ :: levenshtein1 _ :: Validator _ :: db _ :: Nil
    val levenshteinRoute2 = Validator _ :: levenshtein2 _ :: Validator _ :: db _ :: syncAll _ ::  Nil

    // ############ start processing routes

    startNormal(dbRoute)(init)

    if (MagicStrings.GERMANETPATH != null && MagicStrings.GERMANETFREQUENCIESPATH != null) {
      startNormal(germaNetroute1)(init)
      startNormal(germaNetroute2)(init)
    }
    if (MagicStrings.thesaurusDatabaseName != null && MagicStrings.thesaurusDatabaseUrl != null && MagicStrings
      .thesaurusLogin != null && MagicStrings.thesaurusPassword != null) {
      startNormal(thesaurusRoute)(init)
    }

    startNormal(stemmerRoute)(init)
    startNormal(colognePhonetics)(init)
    startNormal(levenshteinRoute)(init)
    startNormal(levenshteinRoute)(init)
    startNormal(levenshteinRoute2)(init)
  }


  def startNormal (pList: List[MPF]) (initValue: PLUGIN_RESULT) : Unit = {
      pList.foldLeft[PLUGIN_RESULT](initValue)({
        (b, a) => a(b)
      })
  }
}
