package unipotsdam.gf.modules.communication.view;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import unipotsdam.gf.modules.communication.model.SampleAnswer;
import unipotsdam.gf.modules.communication.service.SampleService;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/example")
public class SampleView {

    Logger log = LoggerFactory.getLogger(SampleView.class);

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{name}")
    public SampleAnswer helloWorld(@PathParam("name") String name) {
        SampleService sampleService = new SampleService();
        SampleAnswer sampleAnswer = sampleService.provideSampleAnswer(name);
        log.info("HelloWorldview helloWorld Method answered: {}",sampleAnswer);
        return sampleAnswer;
    }
}
