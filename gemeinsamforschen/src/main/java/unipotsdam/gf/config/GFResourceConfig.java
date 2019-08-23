package unipotsdam.gf.config;

import org.glassfish.jersey.server.ResourceConfig;

public class GFResourceConfig extends ResourceConfig {

    public GFResourceConfig() {
        register(GFApplicationBinderFactory.instance());
        packages("unipotsdam.gf");
    }
}
