package unipotsdam.gf.modules.group;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.common.collect.Lists;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.utilities.ServiceLocatorUtilities;
import org.junit.Before;
import org.junit.Test;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;
import unipotsdam.gf.config.GFApplicationBinder;
import unipotsdam.gf.exceptions.CompbaseDownException;
import unipotsdam.gf.exceptions.WrongNumberOfParticipantsException;
import unipotsdam.gf.modules.group.learninggoals.CompBaseMatcher;
import unipotsdam.gf.modules.group.learninggoals.PreferenceData;
import unipotsdam.gf.modules.group.preferences.groupal.request.*;
import unipotsdam.gf.modules.project.Project;
import unipotsdam.gf.modules.user.User;

import javax.inject.Inject;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class CompBaseTest {

    private PodamFactory factory = new PodamFactoryImpl();

    @Inject
    private CompBaseMatcher compBaseMatcher;


    @Before
    public void setUp() throws Exception{
        //final ServiceLocator locator = ServiceLocatorUtilities.bind(new TestGFApplicationBinder());
        final ServiceLocator locator = ServiceLocatorUtilities.bind(new GFApplicationBinder());
        locator.inject(this);
    }

    @Test
    public void testCompBase()
            throws JsonProcessingException, JAXBException, WrongNumberOfParticipantsException, CompbaseDownException {

        Project project = factory.manufacturePojo(Project.class);

        for (int i = 0; i<30;i++) {
            User user = factory.manufacturePojo(User.class);
            PreferenceData data = new PreferenceData();
            ArrayList<String> competences = new ArrayList<>();
            String competence1 = "Studierende interessieren sich für Gemüse";
            String competence2 = "Studierende interessieren sich für Spargel";
            String competence3 = "Studierende interessieren sich für Spagetti";
            competences.add(competence1);
            competences.add(competence2);
            competences.add(competence3);
            data.setCompetences(competences);
            String[] tags = new String[]{"1", "2", "drei", "vier", "fünf"};
            data.setTagsSelected(Arrays.asList(tags));
            compBaseMatcher.sendPreferenceData(project.getName(), user.getName(), data);
        }

        List<Group> groups = compBaseMatcher.calculateGroups(project);
        assertFalse(groups.isEmpty());


    }

}
