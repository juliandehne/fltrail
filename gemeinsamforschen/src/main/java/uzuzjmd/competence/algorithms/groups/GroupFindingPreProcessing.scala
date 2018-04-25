package uzuzjmd.competence.algorithms.groups

import java.util

import uzuzjmd.competence.algorithms.groups.drools.{DLearner, DLearnerPair}
import uzuzjmd.competence.algorithms.groups.optaplanner.SimilarityDBResult
import uzuzjmd.competence.persistence.SQLSimilarityAlgorithmStorage
import uzuzjmd.competence.util.LanguageConverter
import scala.collection.JavaConverters._
import scala.collection.mutable


/**
  * Created by dehne on 06.12.2017.
  */
class GroupFindingPreProcessing(projectId: String) extends LanguageConverter {

  val manager = new SQLSimilarityAlgorithmStorage
  val rawGroups: util.ArrayList[util.ArrayList[AnyRef]] = manager.getSimilaritesForProject(projectId)
  val users: util.ArrayList[String] = manager.getAllUserForProject(projectId);
  val userNumber = users.size();
  val groupsInScala: mutable.Buffer[Array[AnyRef]] = rawGroups.asScala.map(x => x.asScala.toArray)
  //val filteredGroup: mutable.Buffer[(String, String)] = groupsInScala.map(z => (z(0).toString, z(1).toString))
  val userTriple: mutable.Buffer[(String, String, Double)] = groupsInScala.map(z => (z(0).toString, z(1).toString, z(2)
    .asInstanceOf[Double]))

  // all the found pairs
  //val filteredGroup2: mutable.Buffer[(String, String)] = filteredGroup.filter(a => filteredGroup.toList.contains(a._2, a._1))
  // all the found users
  //val foundUsers = filteredGroup2.map(x => x._1).toList.asJava
 /* // nicht gefundene user
  val notFoundUsers = users.asScala.filter(!foundUsers.contains(_)).toList.asJava*/
  // sortedUserTriple
  val sortedUserTriple = userTriple.sortWith((a, b) => (a._3 > b._3))


  def getSimilarityResults(): SimilarityDBResult = {
    val userTriplesJava = userTriple.map(x => new DLearnerPair(x._1, x._2, x._3)).toList.asJava
    val result = new SimilarityDBResult(userTriplesJava, users.map(x=>new DLearner(x)).asJava, getNumberOf3Groups
    (userNumber),
      getNumberOf4Groups
    (userNumber), projectId)
    return result;

  }

  /*def getGroupScore(input: LearningGroup): Int = {
    val learningUsers = input.getLearningUser1.getUserId :: input.getLearningUser2.getUserId :: input
      .getLearningUser3.getUserId :: input.getLearningUser4.getUserId :: Nil
    val score: Double =  userTriple.filter(x=>learningUsers.contains(x._1) && learningUsers.contains(x._2)).map(y=>y._3).reduce((a, b)=>a+b)
    return score.toInt
  }
*/


  // (number % 3) + (Math.floor(number/3)-(number%3)) = n für alle Zahlen größer als 5
  def getNumberOf4Groups(number: Integer): Integer = {
    if (number < 5) {
      throw new Exception("groupsize must excedes 5 to be computed")
    }
    val result = (number % 3);
    return result
  }


  def getNumberOf3Groups(number: Integer): Integer = {
    if (number < 5) {
      throw new Exception("groupsize must excedes 5 to be computed")
    }

    val result  =((number / 3) - (number % 3));
    return result
  }

}
