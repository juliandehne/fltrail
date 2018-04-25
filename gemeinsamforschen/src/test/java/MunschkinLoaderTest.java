import org.junit.Test;
import unipotsdam.gf.munschkin.controller.MunschkinLoader;
import unipotsdam.gf.munschkin.model.Munschkin;


/**
 * Created by dehne on 24.04.2018.
 */
public class MunschkinLoaderTest {
    @Test
    public void testMunschKinLoader() {
        MunschkinLoader m = new MunschkinLoader();
        Munschkin loadedM = m.loadMunschkin(1);
        assert loadedM != null;
        System.out.print(loadedM.toString());
    }
}
