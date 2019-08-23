package unipotsdam.gf.modules.group;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.utilities.ServiceLocatorUtilities;
import org.junit.Before;
import org.junit.Test;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;
import unipotsdam.gf.config.GFApplicationBinder;
import unipotsdam.gf.config.GFApplicationBinderFactory;
import unipotsdam.gf.modules.group.preferences.database.ProfileDAO;
import unipotsdam.gf.modules.group.preferences.database.ProfileQuestion;
import unipotsdam.gf.modules.group.preferences.groupal.*;

import unipotsdam.gf.modules.group.preferences.groupal.request.*;
import unipotsdam.gf.modules.group.preferences.groupal.response.ResponseHolder;
import unipotsdam.gf.modules.group.preferences.survey.GroupWorkContext;
import unipotsdam.gf.modules.group.preferences.survey.SurveyMapper;
import unipotsdam.gf.modules.project.Project;
import unipotsdam.gf.modules.group.preferences.groupal.JacksonPojoToJson;
import unipotsdam.gf.modules.user.User;

import javax.inject.Inject;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.util.*;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class GroupAlTest {

    private PodamFactory factory = new PodamFactoryImpl();
    private Marshaller jaxbMarshaller;

    @Inject
    private PGroupAlMatcher pGroupAlMatcher;

    @Inject
    private ProfileDAO profileDAO;

    @Inject
    private SurveyMapper surveyMapper;

    @Before
    public void setUp() throws Exception {
        //final ServiceLocator locator = ServiceLocatorUtilities.bind(new TestGFApplicationBinder());
        final ServiceLocator locator = ServiceLocatorUtilities.bind(GFApplicationBinderFactory.instance());
        locator.inject(this);
        //
        JAXBContext jaxbContext = JAXBContext.newInstance(ParticipantsHolder.class);
        jaxbMarshaller = jaxbContext.createMarshaller();
    }


    @Test
    public void testMarshalling() throws JAXBException {
        ParticipantsHolder participantsHolder = factory.manufacturePojo(ParticipantsHolder.class);
        assertNotNull(participantsHolder);
        //jaxbMarshaller.marshal(participantsHolder, System.out);
    }

    /**
     * testing the groupal mono interface
     *
     * @throws JAXBException
     */
    @Test
    public void testCreateRealExample() throws JAXBException {
        ParticipantsHolder participantsHolder = new ParticipantsHolder();
        List<UsedCriterion> criterions2 = new ArrayList<>();
        UsedCriterion firstCriterion2 = new UsedCriterion();
        firstCriterion2.setIsHomogeneous("true");
        firstCriterion2.setMaxValue(10f);
        firstCriterion2.setMinValue(0f);
        firstCriterion2.setName("Homodestacistität");
        firstCriterion2.setWeight(1);
        firstCriterion2.setValueCount(2);
        criterions2.add(firstCriterion2);
        UsedCriteria usedCriteria = new UsedCriteria(criterions2);
        participantsHolder.setUsedCriteria(usedCriteria);

        for (int i = 0; i < 30; i++) {
            Random rn = new Random();
            // the participants
            Participants participant = new Participants();
            participant.setId(i);
            Value value = new Value();
            value.setName("a1");
            value.setValue(rn.nextInt(10));
            Value value2 = new Value();
            // es ist aus absurden Gründen wichtig, dass der Name genau so gesetzt ist
            value2.setName("value0");
            value2.setValue(rn.nextInt(10));
            ArrayList<Value> values = new ArrayList<>();
            values.add(value);
            values.add(value2);

            Criterion firstCriterion = new Criterion();
            firstCriterion.setIsHomogeneous("true");
            firstCriterion.setMaxValue(10f);
            firstCriterion.setMinValue(0f);
            firstCriterion.setName("value1");
            firstCriterion.setWeight(1);
            firstCriterion.getValues().add(value);
            firstCriterion.getValues().add(value2);
            participant.getCriterion().add(firstCriterion);

            //Criterion firstCriterionInstance = (Criterion) BeanUtils.cloneBean(firstCriterion);

            participantsHolder.getParticipants().add(participant);

        }
        assertNotNull(participantsHolder);
        jaxbMarshaller.marshal(participantsHolder, System.out);
    }

    @Test
    public void testGroupAl() throws Exception {


        Project project = surveyMapper.createNewProject(GroupWorkContext.dota_1);

        // utility
        Random random = new Random();

        // add questions
        //SurveyPreparation.main(new String[0]);
        
        // get variables
        List<ProfileQuestion> questions = profileDAO.getQuestions(GroupWorkContext.dota_1);

        // add answers
        for (int i = 0; i < 31; i++) {
            User user = factory.manufacturePojo(User.class);
            HashMap<String, String> data = new HashMap<>();
            for (ProfileQuestion question : questions) {
                int id = question.getId();
                int rateAnswer = random.nextInt(5);
                data.put(id + "", rateAnswer+"");
            }
            data.put(SurveyMapper.EMAIL1, user.getEmail());
            data.put(SurveyMapper.NICKNAME1, user.getName());
            project.setGroupWorkContext(GroupWorkContext.dota_1);
            surveyMapper.saveData(data, project, null);
            Thread.sleep(15);
        }

        // calculate groups
        List<Group> d1_test = pGroupAlMatcher.calculateGroups(project, 3);
        assertTrue(!d1_test.isEmpty());
        assertTrue(d1_test.size() > 0);
    }

    @Test
    public void serializeResponse() throws JAXBException, JsonProcessingException {
        ResponseHolder responseHolder = factory.manufacturePojo(ResponseHolder.class);
        JacksonPojoToJson.writeExample(responseHolder.getClass());
    }


}
