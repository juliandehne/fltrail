import unipotsdam.gf.munchkin.interfaces.IMunschkin;
import unipotsdam.gf.munchkin.controller.MunchkinImpl;
import unipotsdam.gf.munchkin.model.Munschkin;
import org.junit.Test;

/**
 * Created by dehne on 24.04.2018.
 */
public class MunschkinLoaderTest {
    @Test
    public void testMunschKinLoader() {
        IMunschkin m = new MunchkinImpl();
        // Nützlich weil: IMunschkin m2 = new HendriksMunchkinImpl();

        // ab hier ist es dem Code egal, welche Implementation hinter dem Interface steht
        Munschkin loadedM = m.getMunschkin(1);
        assert loadedM != null;
        System.out.print(loadedM.toString());
    }
}
