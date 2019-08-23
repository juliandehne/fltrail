package unipotsdam.gf.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GFApplicationBinderFactory {

    private static GFApplicationBinder gfApplicationBinder;

    private final static Logger log = LoggerFactory.getLogger(GFApplicationBinderFactory.class);

    public static synchronized GFApplicationBinder instance() {
        if (gfApplicationBinder == null) {
            log.info("CREATING gfApplicationBinder");
            gfApplicationBinder = new GFApplicationBinder();
            log.info("FINISHED creating gfApplicationBinder"  + gfApplicationBinder);
        }
        return gfApplicationBinder;
    }
}
