package unipotsdam.gf.healthchecks;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class HealthcheckTest {

    @Test
    public void checkIfRocketIsAvailable() {
        Boolean result = HealthChecks.isRocketOnline();
        assertTrue(result);

    }

    @Test
    public void checkIfCompbaseIsAvailable() {
        Boolean result2 = HealthChecks.isCompBaseOnline();
        assertTrue(result2);
    }
}
