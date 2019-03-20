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

    private final String excelFileName;

    @Inject
    ProfileDAO profileDAO;

    public ItemWriter(String excelFileName) {
        this.excelFileName = excelFileName;
    }

    public void writeItems() throws Exception {


        final ServiceLocator locator = ServiceLocatorUtilities.bind(new GFApplicationBinder());
        locator.inject(this);

        String path = System.getProperty("user.dir") + "/src/main/resources/"+excelFileName;
        java.util.List<ItemSet> itemSets = Poiji.fromExcel(new File(path), ItemSet.class);
        itemSets.remove(itemSets.size() - 1);

        for (ItemSet itemSet : itemSets) {

            // write variables
            profileDAO.persistProfileVariable(itemSet);

            // write items
            String[] englishItems = itemSet.getItemEnglish().replaceAll("\n", "").trim().split("\\.");
            String[] germanItems = itemSet.getItemGerman().replaceAll("\n", "").trim().split("\\.");



            if (englishItems.length != germanItems.length) {
                throw new Exception("Übersetzung ist wohl nicht korrekt");
            }


            for (int i = 0; i < englishItems.length; i++) {

                ScaledProfileQuestion scaledProfileQuestion = new ScaledProfileQuestion();
                String germanItem = germanItems[i].trim().replaceAll("  ", " ");
                String englishItem = englishItems[i].trim().replaceAll("  ", " ");
                String polarityMarker = "(-)";
                if (germanItem.contains(polarityMarker)) {
                    String polarityMarkerRegex = "\\(-\\)";
                    germanItem = germanItem.replaceAll(polarityMarkerRegex, "");
                    englishItem = englishItem.replaceAll(polarityMarkerRegex, "");
                    scaledProfileQuestion.setPolarity(false);
                } else {
                    scaledProfileQuestion.setPolarity(true);
                }

                scaledProfileQuestion.setQuestion(germanItem);
                scaledProfileQuestion.setQuestion_en(englishItem);
                scaledProfileQuestion.setScaleSize(5);
                scaledProfileQuestion.setSubvariable(itemSet.getSubVariable().replaceAll("\n", "").trim());
                profileDAO.persist(scaledProfileQuestion);
            }
        }

    }
}
