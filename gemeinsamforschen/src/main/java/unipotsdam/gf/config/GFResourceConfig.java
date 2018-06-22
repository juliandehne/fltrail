package unipotsdam.gf.config;

import org.glassfish.jersey.server.ResourceConfig;

public class GFResourceConfig extends ResourceConfig {

    public GFResourceConfig() {
        register(new GFApplicationBinder());
        packages("unipotsdam.gf");
    }
}
