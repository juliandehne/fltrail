package unipotsdam.gf.service;

/**
 * Created by dehne on 24.04.2018.
 */

import unipotsdam.gf.controller.MunschkinLoader;
import unipotsdam.gf.model.Munschkin;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/hello")
public class RestyMunschkin {


    // This method is called if HTML is request
    @GET
    @Produces(MediaType.TEXT_HTML)
    @Path("/{id}")
    public String sayHtmlHello(@PathParam("id") String id) {
        MunschkinLoader munschkinLoader = new MunschkinLoader();
        Munschkin munschkin = munschkinLoader.loadMunschkin(Integer.parseInt(id));
        return "<html> " + "<title>" + "Hello Munschkin" + "</title>" + "<body><h1>" + munschkin
                .toString() + "</body></h1>" + "</html> ";
    }


}
