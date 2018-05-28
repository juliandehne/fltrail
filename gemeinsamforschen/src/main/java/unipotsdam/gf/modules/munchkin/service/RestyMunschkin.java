package unipotsdam.gf.modules.munchkin.service;

/**
 * Created by dehne on 24.04.2018.
 */

import unipotsdam.gf.interfaces.IMunschkin;
import unipotsdam.gf.modules.munchkin.controller.MunchkinImpl;
import unipotsdam.gf.modules.munchkin.model.Munschkin;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/api")
public class RestyMunschkin {


    // This method is called if HTML is request
    @GET
    @Produces(MediaType.TEXT_HTML)
    @Path("/hello/{id}")
    public String sayHtmlHello(@PathParam("id") String id) {
        IMunschkin munchkinImpl = new MunchkinImpl();
        Munschkin munschkin = munchkinImpl.getMunschkin(Integer.parseInt(id));
        return "<html> " + "<title>" + "Hello Munschkin" + "</title>" + "<body><h1>" + munschkin
                .toString() + "</body></h1>" + "</html> ";
    }

    // This method is called if HTML is request
    @GET
    @Produces(MediaType.APPLICATION_XML)
    @Path("/munschkin/{id}")
    public Munschkin getMunschkin(@PathParam("id") String id) {
        IMunschkin munchkinImpl = new MunchkinImpl();
        Munschkin munschkin = munchkinImpl.getMunschkin(Integer.parseInt(id));
        return munschkin;
    }


}
