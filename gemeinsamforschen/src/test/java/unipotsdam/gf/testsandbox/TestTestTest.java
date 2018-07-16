package unipotsdam.gf.testsandbox;


import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.utilities.ServiceLocatorUtilities;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import unipotsdam.gf.config.GFApplicationBinder;
import unipotsdam.gf.core.testsandbox.TestListInterface;

import javax.inject.Inject;
import java.util.ArrayList;

import static junit.framework.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class TestTestTest {


    @Spy
    java.util.List<String> spiedList = new ArrayList<String>();


    @Inject
    TestListInterface spiedList2;



    @Inject
    @Spy
    TestListInterface spiedList3;

    @Before
    public void setUp() {
        final ServiceLocator locator = ServiceLocatorUtilities.bind(new GFApplicationBinder());
        locator.inject(this);
    }


    // GEHT
    @Test
    public void howSpiesWork() {
        java.util.List<String> spiedList = Mockito.spy(new ArrayList<String>());
        Mockito.spy(spiedList);

        spiedList.add("one");
        spiedList.add("two");

        Mockito.verify(spiedList).add("one");
        Mockito.verify(spiedList).add("two");

        assertEquals(2, spiedList.size());
    }


    // GEHT
    @Test
    public void howSpiesWorkWithAnnotation() {

        spiedList.add("one");
        spiedList.add("two");

        Mockito.verify(spiedList).add("one");
        Mockito.verify(spiedList).add("two");

        assertEquals(2, spiedList.size());
    }

    // GEHT
    @Test
    public void howSpiesWorkWithInjection() {

        java.util.List<String> spiedList = Mockito.spy(spiedList2);

        spiedList.add("one");
        spiedList.add("two");

        Mockito.verify(spiedList).add("one");
        Mockito.verify(spiedList).add("two");

        assertEquals(2, spiedList.size());
    }

    // GEHT NICHT!
    @Test
    public void howSpiesWorkWithInjectionAndAnnotation() {

        spiedList3.add("one");
        spiedList3.add("two");

        Mockito.verify(spiedList3).add("one");
        Mockito.verify(spiedList3).add("two");

        assertEquals(2, spiedList3.size());
    }
}
