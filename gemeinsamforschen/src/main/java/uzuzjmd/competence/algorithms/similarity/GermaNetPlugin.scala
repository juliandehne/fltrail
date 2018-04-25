package uzuzjmd.competence.algorithms.similarity

import java.lang.{Double, Long}
import java.util

import config.MagicStrings
import de.tuebingen.uni.sfs.germanet.api.{ConRel, GermaNet, Synset}
import de.tuebingen.uni.sfs.germanet.relatedness.{Frequency, Relatedness}
import uzuzjmd.competence.persistence.SqlDictionary

import scala.collection.JavaConverters._
import scala.collection.mutable


/**
  * Created by dehne on 13.09.2017.
  */
object GermaNetPlugin {
  val gnet = new GermaNet(MagicStrings.GERMANETPATH, true);
  val ics: util.HashMap[String, Double] = Frequency.loadIC(MagicStrings.GERMANETFREQUENCIESPATH + "/frequencies.csv")
  val frequencies: util.HashMap[String, Long] = Frequency.loadFreq(MagicStrings.GERMANETFREQUENCIESPATH + "/frequencies.csv")
  Frequency.assignFrequencies(MagicStrings.GERMANETFREQUENCIESPATH, gnet)
  val rel: Relatedness = new Relatedness(gnet)

  def getSynStringsJava(input: String): util.List[String] = {
    return getSynStrings(input).asJava
  }

  def getSynStrings(input: String): List[String] = {
    val cons: List[ConRel] = ConRel.values().toList
    return cons.flatMap(getSynsets(_, gnet, input))
  }

  def getSynsets(rel: ConRel, gnet: GermaNet, input: String): List[String] = {
    val tmp1: mutable.Buffer[Synset] = gnet.getSynsets(input).asScala.map(x => x.getRelatedSynsets(rel).asScala).flatten
    return tmp1.map(x => x.getAllOrthForms.asScala).flatten.toList
  }

  def getSimStrings(input: String) : java.util.List[String] ={

    //Frequency.cleanLists(MagicStrings.GERMANETFREQUENCIESPATH); //slow; comment out if not needed

    val result = new util.HashSet[String]()
    val it = gnet.getSynsets(input).iterator();
    while (it.hasNext) {
      val synset1 = it.next()
      val it2 = SqlDictionary.getCatchwords.iterator()
      while (it2.hasNext) {
        val catchword = it2.next();
        val simStrings = getSynStrings(catchword);
        val synset2List = simStrings.map(x=>gnet.getSynsets(x).asScala).flatten
        val it3 = synset2List.iterator
        while (it3.hasNext) {
          val synset2 = it3.next();
          //println("\nPath measure: %.2f", rel.path(synset1, synset2).getResult)
          //println("\nWu and Palmer's measure : %.2f", rel.wuAndPalmer(synset1, synset2).getResult)
          //println("\nLeacock and Chodorow's measure : %.2f", rel.leacockAndChodorow(synset1, synset2).getResult)
          //println("\nResnik's measure: %.2f", rel.resnik(synset1, synset2, frequencies).getResult)
          //println("\nLin's measure: %.2f", rel.lin(synset1, synset2, frequencies).getResult)
          //println("\nJiang and Conrath's measure: %.2f", rel.jiangAndConrath(synset1, synset2, frequencies)
          // .getResult)
          //println("\nHirst and St-Onge's measure: %.2f", rel.hirstAndStOnge(synset1, synset2).getResult)
          //println("\nLesk's measure (adapted): %.2f", rel.lesk(synset1, synset2, gnet).getResult)
          val simResult = rel.leacockAndChodorow(synset1, synset2).getResult
          if (simResult >= 3.5) {
            result.add(catchword);
          }
        }
      }
    }
    return result.asScala.toList.asJava
  }
}
