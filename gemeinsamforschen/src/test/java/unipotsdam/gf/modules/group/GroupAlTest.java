package unipotsdam.gf.modules.group;

import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.utilities.ServiceLocatorUtilities;
import org.junit.Before;
import org.junit.Test;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;
import unipotsdam.gf.core.database.TestGFApplicationBinder;
import unipotsdam.gf.modules.group.preferences.groupal.*;

import unipotsdam.gf.modules.user.User;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import org.apache.commons.beanutils.*;

public class GroupAlTest {

    private PodamFactory factory = new PodamFactoryImpl();
    private Marshaller jaxbMarshaller;

    @Before
    public void setUp() throws Exception{
        final ServiceLocator locator = ServiceLocatorUtilities.bind(new TestGFApplicationBinder());
        //final ServiceLocator locator = ServiceLocatorUtilities.bind(new GFApplicationBinder());
        locator.inject(this);
        //
        JAXBContext jaxbContext = JAXBContext.newInstance(ParticipantsHolder.class);
        jaxbMarshaller = jaxbContext.createMarshaller();
    }
    

    @Test
    public void testMarshalling() throws JAXBException {
      

        //
        ParticipantsHolder participantsHolder = factory.manufacturePojo(ParticipantsHolder.class);
        assertNotNull(participantsHolder);
        //jaxbMarshaller.marshal(participantsHolder, System.out);
    }

    @Test
    public void testCreateRealExample()
            throws JAXBException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
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

        for (int i = 0; i< 30; i++){
            Random rn = new Random();
            // the participants
            Participant participant = new Participant();
            participant.setId(i);
            Value value = new Value();
            value.setName("a1");
            value.setValue(rn.nextInt(10));
            Value value2 = new Value();
            value2.setName("a2");
            value2.setValue(rn.nextInt(10));
            ArrayList<Value> values = new ArrayList<>();
            values.add(value);
            values.add(value2);

            Criterion firstCriterion = new Criterion();
            firstCriterion.setIsHomogeneous("true");
            firstCriterion.setMaxValue(10f);
            firstCriterion.setMinValue(0f);
            firstCriterion.setName("xzzz" +i);
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
}
