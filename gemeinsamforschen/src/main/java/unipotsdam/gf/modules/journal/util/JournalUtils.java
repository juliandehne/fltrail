package unipotsdam.gf.modules.journal.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import unipotsdam.gf.core.management.utils.Category;
import unipotsdam.gf.modules.journal.model.Visibility;

public class JournalUtils {

    public static final Logger log = LoggerFactory.getLogger(JournalUtils.class);

    public static Category stringToCategory(String category) {
        // If String does not match enum IllegalArgumentException
        Category c;
        try {
            c = Category.valueOf(category);
        } catch (IllegalArgumentException e) {
            c = Category.TITEL;
            //TODO extra Category for fail?
            JournalUtils.log.debug("Illegal argument for visibility, default to TITLR");
        }
        return c;
    }

    public static Visibility stringToVisibility(String visibility) {
        // If String does not match enum IllegalArgumentException
        Visibility v;
        try {
            v = Visibility.valueOf(visibility);
        } catch (IllegalArgumentException e) {
            v = Visibility.MINE;
            JournalUtils.log.debug("Illegal argument for visibility, default to MINE");
        }
        return v;
    }
}
