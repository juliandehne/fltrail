/*
package uzuzjmd.competence.algorithms.groups.drools

import java.util

import org.kie.api.io.ResourceType
import org.kie.internal.definition.KnowledgePackage
import org.kie.internal.runtime.StatefulKnowledgeSession
import org.kie.internal.{KnowledgeBaseFactory, KnowledgeBase}
import org.kie.internal.builder.{KnowledgeBuilderFactory, KnowledgeBuilder}
import org.kie.internal.io.ResourceFactory
import uzuzjmd.competence.algorithms.groups.GroupFindingPreProcessing
import uzuzjmd.competence.algorithms.groups.optaplanner.SimilarityDBResult
import uzuzjmd.competence.shared.group.{LearningGroup, GroupData}
import uzuzjmd.competence.util.LanguageConverter


import scala.collection.JavaConverters._
import scala.collection.immutable.Iterable
import scala.collection.mutable

/**
  * Created by dehne on 13.12.2017.
  */
object GroupFinding{

   def convert(projectId: String) : GroupData = {
     val finder = new GroupFindingPreProcessing(projectId)
     val similarityResult: SimilarityDBResult = finder.getSimilarityResults()

     val kbuilder: KnowledgeBuilder = KnowledgeBuilderFactory.newKnowledgeBuilder
     // this will parse and compile in one step
     kbuilder.add(ResourceFactory.newFileResource("C:\\dev\\phd\\competence-database\\competence-database\\src\\main\\scala\\uzuzjmd\\competence\\algorithms\\groups\\drools\\groups.drl"), ResourceType.DRL)
     // Check the builder for errors
     if (kbuilder.hasErrors) {
       System.out.println(kbuilder.getErrors.toString)
       throw new RuntimeException("Unable to compile \"HelloWorld.drl\".")
     }
     // get the compiled packages (which are serializable)
     val pkgs: java.util.Collection[KnowledgePackage] = kbuilder.getKnowledgePackages
     // add the packages to a knowledgebase (deploy the knowledge packages).
     val kbase: KnowledgeBase = KnowledgeBaseFactory.newKnowledgeBase
     kbase.addKnowledgePackages(pkgs)
     val ksession: StatefulKnowledgeSession = kbase.newStatefulKnowledgeSession

     // adding the artefacts to the knowledge database

     // add all the groups
     for (i <- 1 to similarityResult.getNumberOf3Groups) {
       ksession.insert(new DGroup(0,3,i))
     }
     for (j <- 1 to similarityResult.getNumberOf4Groups) {
       ksession.insert(new DGroup(0,4,j))
     }
     // get all the learners
     val users: mutable.Buffer[DLearner] = similarityResult.getUsers.asScala
     users.foreach(z=>ksession.insert(z))

     // get all the learning pairs
     val pairs = similarityResult.getUserPairs.asScala
     pairs.foreach(ksession.insert(_))


     ksession.fireAllRules
     ksession.dispose

     //
     val result = new GroupData
     val groups: Map[Integer, mutable.Buffer[DLearner]] = users.groupBy(_.getGroupId)
     val groupsJava: util.List[LearningGroup] = groups.map(x=>(new LearningGroup((x._2.map(y=>y.getLearnerId)).asJava)
       )).toList.asJava
     result.setGroups(groupsJava)
     return result
   }

}
*/
