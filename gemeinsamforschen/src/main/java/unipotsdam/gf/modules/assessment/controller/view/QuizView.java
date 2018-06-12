package unipotsdam.gf.modules.assessment.controller.view;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import unipotsdam.gf.modules.assessment.controller.model.Quiz;
import unipotsdam.gf.modules.communication.view.SampleView;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


class QuizView {


    Logger log = LoggerFactory.getLogger(SampleView.class);

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("quiz/{projectId}/{quizId}")

    public Response exampleQuiz(@PathParam("projectId") String name, @PathParam("quizId") String quiz) {
        String[] correctAnswers = new String[2];
        correctAnswers[0] = "42";
        correctAnswers[1] = "24";
        String[] wrongAnswers = {"a god created creature", "a sum of my mistakes"};
        if (false){
            return Response.status(Response.Status.NOT_FOUND).entity("Entity not found for UUID: " + name).build();
        }
        Quiz sampleQuiz = new Quiz("multiple","Who am I and if so, how many?", correctAnswers,wrongAnswers);
        log.info("HelloWorldview helloWorld Method answered: "+ sampleQuiz.toString());
        return Response.ok(sampleQuiz.toString(), MediaType.APPLICATION_JSON).build();
    }
}
