package unipotsdam.gf.modules.group.preferences.excel;


import com.poiji.bind.Poiji;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.utilities.ServiceLocatorUtilities;
import unipotsdam.gf.config.GFApplicationBinder;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class ItemsImporter {
    public static void main(String[] args) throws Exception {
        ItemWriter itemWriter = new ItemWriter();
        itemWriter.writeItems();
    }
}
