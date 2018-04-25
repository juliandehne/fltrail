package uzuzjmd.competence.algorithms.groups.drools;

import config.MagicStrings;
import org.kie.api.io.ResourceType;
import org.kie.internal.KnowledgeBase;
import org.kie.internal.KnowledgeBaseFactory;
import org.kie.internal.builder.KnowledgeBuilder;
import org.kie.internal.builder.KnowledgeBuilderFactory;
import org.kie.internal.definition.KnowledgePackage;
import org.kie.internal.io.ResourceFactory;
import org.kie.internal.runtime.StatefulKnowledgeSession;
import uzuzjmd.competence.algorithms.groups.GroupFindingPreProcessing;
import uzuzjmd.competence.algorithms.groups.optaplanner.SimilarityDBResult;
import uzuzjmd.competence.shared.group.GroupData;
import uzuzjmd.competence.shared.group.LearningGroup;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by dehne on 14.12.2017.
 */
public class GroupFinding2 {
    public GroupData convert(String projectId) {

        GroupFindingPreProcessing  finder = new GroupFindingPreProcessing(projectId);
        SimilarityDBResult similarityResult = finder.getSimilarityResults();


        GroupData data1 = doFiveGroups(similarityResult);
        if (data1 != null) {
            return data1;
        }


        // initiateDroolsZeug
        StatefulKnowledgeSession ksession = droolsZeug();

        for (int i = 1; i <= similarityResult.getNumberOf3Groups(); i++) {
            ksession.insert(new DGroup(0,3,i));
        }
        for (int j = 1; j <=  similarityResult.getNumberOf4Groups(); j++) {
            ksession.insert(new DGroup(0,4,j+similarityResult.getNumberOf3Groups()));
        }
        // get all the learners
        List<DLearner> users = similarityResult.getUsers();
        for (DLearner user : users) {
            ksession.insert(user);
        }

        // get all the learning pairs
        List<DLearnerPair> userPairs = similarityResult.getUserPairs();
        for (DLearnerPair userPair : userPairs) {
            ksession.insert(userPair);
        }

        terminateDroolsZeug(ksession);

        GroupData data = new GroupData();

        int numberOfTotalGroups = similarityResult.getNumberOf3Groups() + similarityResult.getNumberOf4Groups();

        for (int l = 1; l<=numberOfTotalGroups;l++) {
            LearningGroup learningGroup = new LearningGroup(l);
            for (DLearner user : users) {
                if (user.getGroupId() == l) {
                    learningGroup.getUsers().add(user.getLearnerId());
                }
            }
            data.getGroups().add(learningGroup);
        }


        return data;
    }

    public GroupData doFiveGroups(SimilarityDBResult similarityResult) {
        // wenn es weniger als 5 dudes sind
        if (similarityResult.getUsers().size() <= 5) {
            GroupData data = new GroupData();
            LearningGroup learningGroup = new LearningGroup();
            ArrayList<String> users = similarityResult.getUsers().stream().map(DLearner::getLearnerId)
                    .collect(Collectors.toCollection(ArrayList::new));
            learningGroup.setUsers(users);
            data.getGroups().add(learningGroup);
            learningGroup.setId("FiveGroupDefault".hashCode());
            return data;
        }
        return null;
    }

    public void terminateDroolsZeug(StatefulKnowledgeSession ksession) {
        ksession.fireAllRules();
        ksession.dispose();
    }

    public StatefulKnowledgeSession droolsZeug() {
        // zeug
        KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
        // this will parse and compile in one step
        kbuilder.add(ResourceFactory.newFileResource
                (MagicStrings.GROUPRULESPATH), ResourceType.DRL);
        // Check the builder for errors
        if (kbuilder.hasErrors()) {
            System.out.println(kbuilder.getErrors().toString());
            throw new RuntimeException("Unable to compile \"group.drl\".");
        }
        // get the compiled packages (which are serializable)
        java.util.Collection<KnowledgePackage> pkgs = kbuilder.getKnowledgePackages();
        // add the packages to a knowledgebase (deploy the knowledge packages).
        KnowledgeBase kbase = KnowledgeBaseFactory.newKnowledgeBase();
        kbase.addKnowledgePackages(pkgs);
        return kbase.newStatefulKnowledgeSession();
    }
}
