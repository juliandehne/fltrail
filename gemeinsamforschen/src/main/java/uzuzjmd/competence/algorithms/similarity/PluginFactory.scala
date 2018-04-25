package uzuzjmd.competence.algorithms.similarity

import java.lang

import uzuzjmd.competence.algorithms.similarity.TransformationRoutes.PLUGIN_RESULT
import uzuzjmd.competence.persistence.SQLSimilarityAlgorithmStorage

import scala.collection.JavaConverters._;
/**
  * Created by dehne on 18.09.2017.
  */
object PluginFactory extends DefaultInstrumented{



  def Validator (a: PLUGIN_RESULT): PLUGIN_RESULT = {
    // todo implement validation again

    return a
  }


  def Stemmer(a: PLUGIN_RESULT): PLUGIN_RESULT = {
     val result =  processTransformation(a, new WordToStemPluginWrapper(a._2))
     return result;
   }

  // plugin nach plugin
  def germaNetSYN(f: PLUGIN_RESULT): PLUGIN_RESULT = {
    val result = processTransformation(f, new GermaNetPluginWrapper(f._2))
    return result;
  }


  // plugin nach plugin
  def germaNetSim(f: PLUGIN_RESULT): PLUGIN_RESULT = {
    val result = processTransformation(f, new GermanetSimPluginWrapper(f._2))
    return result;
  }


  // plugin nach plugin
  def levenshtein1(f: PLUGIN_RESULT): PLUGIN_RESULT = {
    val result = processTransformation(f,new LevensteinsPlugin(f._2))
    return result;
  }

  // plugin nach plugin
  def levenshtein2(f: PLUGIN_RESULT): PLUGIN_RESULT = {
    val result = processTransformation(f, new Levenshtein2Plugin(f._2))
    return result;
  }

  // plugin nach plugin
  def cologne(f: PLUGIN_RESULT): PLUGIN_RESULT = {
    val result = processTransformation(f, new ColognePhoneticPlugin(f._2))
    return result
  }

  // plugin nach plugin
  def thesaurus(f: PLUGIN_RESULT): PLUGIN_RESULT = {
    val result = processTransformation(f, new OpenThesaurusSynonymPluginWrapper(f._2))
    return result
  }

  def db(f: PLUGIN_RESULT): PLUGIN_RESULT = {
    val nodes = f._1.map(x=>x).asJava;
    val queryManager = new SQLSimilarityAlgorithmStorage
    queryManager.createOrUpdateBatchSimilarityMeasure(f._3, f._2, nodes)

    // TODO instead of writing it to db, store the results somewhere the dictionary
    return f
  }

  def sync (f: PLUGIN_RESULT): PLUGIN_RESULT = {
    val nodes = f._1.map(x=>x).asJava;
    val specialQueries = new SQLSimilarityAlgorithmStorage
    specialQueries.createOrUpdateBatchSimilarityMeasure(f._3, f._2, nodes)

    // TODO take all the results stored in the "db" transformation and write them in the db
    return f
  }

  def syncAll (f: PLUGIN_RESULT): PLUGIN_RESULT = {
    //InMemoryDB.sync();

    // TODO take all the results stored in the "db" transformation and write them in the db
    return f
  }

  private def processTransformation(a: (List[String], lang.Double, String),
                            wordTransformationPlugin: WordTransformationPlugin):
  (List[String], lang.Double, String) = {
    val priority: lang.Double = wordTransformationPlugin.priority;
    if (a._1.length == 0) {
      return a;
    }
    startTime(wordTransformationPlugin.getClass.getSimpleName)
    //SystemOutMuter.stopSystemOut()
    val transformed: List[String] = a._1.map(wordTransformationPlugin.transform(_).asScala).flatten
    //SystemOutMuter.restartSystemOut()
    val length =  a._1.length;
    val transformedLength = transformed.size;
    System.out.println(wordTransformationPlugin.getClass.getSimpleName + " transformed " + length + " to " + transformedLength)
    stopTime(wordTransformationPlugin.getClass.getSimpleName)
    val r = (transformed, priority, a._3)
    r
  }







}
