package unipotsdam.gf.modules.group.preferences.excel;

import com.poiji.bind.Poiji;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.utilities.ServiceLocatorUtilities;
import unipotsdam.gf.config.GFApplicationBinder;
import unipotsdam.gf.modules.group.preferences.database.ProfileDAO;

import javax.inject.Inject;
import java.io.File;
import java.util.ArrayList;

public class ItemWriter {

    @Inject
    ProfileDAO profileDAO;

    public ItemWriter() {

    }

    public void writeItems() {
        final ServiceLocator locator = ServiceLocatorUtilities.bind(new GFApplicationBinder());
        locator.inject(this);

        String path = System.getProperty("user.dir")+"/src/main/resources/groupfindingitems.xls";
        java.util.List<ItemSet> itemSets = Poiji.fromExcel(new File(path), ItemSet.class);

        for (ItemSet itemSet : itemSets) {
            String[] englishItems = itemSet.getItemEnglish().split(".");
            String[] germanItems = itemSet.getItemEnglish().split(".");
            //
        }

    }
}
