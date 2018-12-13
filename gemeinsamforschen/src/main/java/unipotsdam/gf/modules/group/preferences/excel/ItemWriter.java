package unipotsdam.gf.modules.group.preferences.excel;

import com.poiji.bind.Poiji;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.utilities.ServiceLocatorUtilities;
import unipotsdam.gf.config.GFApplicationBinder;
import unipotsdam.gf.modules.group.preferences.database.ProfileDAO;
import unipotsdam.gf.modules.group.preferences.database.ScaledProfileQuestion;

import javax.inject.Inject;
import java.io.File;
import java.util.ArrayList;

public class ItemWriter {

    @Inject
    ProfileDAO profileDAO;

    public ItemWriter() {

    }

    public void writeItems() throws Exception {
        final ServiceLocator locator = ServiceLocatorUtilities.bind(new GFApplicationBinder());
        locator.inject(this);

        String path = System.getProperty("user.dir") + "/src/main/resources/groupfindingitems.xls";
        java.util.List<ItemSet> itemSets = Poiji.fromExcel(new File(path), ItemSet.class);

        for (ItemSet itemSet : itemSets) {

            // write variables
            profileDAO.persistProfileVariable(itemSet);

            // write items
            String[] englishItems = itemSet.getItemEnglish().split(".");
            String[] germanItems = itemSet.getItemEnglish().split(".");

            if (englishItems.length != germanItems.length) {
                throw new Exception("Übersetzung ist wohl nicht korrekt");
            }

            for (int i = 0; i < englishItems.length; i++) {
                ScaledProfileQuestion scaledProfileQuestion = new ScaledProfileQuestion();
                scaledProfileQuestion.setQuestion(germanItems[i]);
                scaledProfileQuestion.setQuestion_en(englishItems[i]);
                scaledProfileQuestion.setScaleSize(5);
                scaledProfileQuestion.setSubvariable(itemSet.getSubVariable());
                profileDAO.persist(scaledProfileQuestion);
            }
        }

    }
}
