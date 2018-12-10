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

    @Test
    public void checkIfMySQLAvailable() {
        Boolean result3 = HealthChecks.isMysqlOnline();
        assertTrue(result3);
    }
}
