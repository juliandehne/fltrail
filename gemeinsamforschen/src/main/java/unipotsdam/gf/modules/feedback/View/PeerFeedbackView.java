package unipotsdam.gf.modules.feedback.View;

import unipotsdam.gf.modules.feedback.Category;
import unipotsdam.gf.modules.feedback.Controller.PeerFeedbackController;
import unipotsdam.gf.modules.feedback.Model.Peer2PeerFeedback;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;

@Path("/peerfeedback")
//@Consumes({"application/json"})
//@Produces({"application/json"})
//@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
//@Produces(MediaType.TEXT_PLAIN)
@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
@Produces(MediaType.APPLICATION_JSON)
public class PeerFeedbackView {

    //private final Logger log = LoggerFactory.getLogger(PeerFeedbackView.class);

    @POST
    @Path("/save")
    public Response createPeerfeedback(@FormParam("text") String text, @FormParam("student") String student, @FormParam("id") String id, @FormParam("reciever") String reciever,
                                       @FormParam("sender") String sender, @FormParam("filename") String filename, @FormParam("category") Category category, @FormParam("timestamp") Long timestamp) {

        // save peerfeedback request in database and receive the new peerfeedback object
        //PeerFeedbackController controller = new PeerFeedbackController();
        //Peer2PeerFeedback pf = controller.createPeer2PeerFeedback(feedback);
        //return Response.ok(pf).build();

        String message = text + student;
        Peer2PeerFeedback feedback = new Peer2PeerFeedback("id", 1234, Category.TITEL, "reciever", "sender", text, "filename");
        PeerFeedbackController controller = new PeerFeedbackController();
        controller.createPeer2PeerFeedback(feedback);


        return Response.ok().build(); //feedback

    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{sender}")
    public Response getPeerfeedback(@PathParam("sender") String sender) {

        PeerFeedbackController controller = new PeerFeedbackController();
        ArrayList<Peer2PeerFeedback> fd = controller.getAllFeedbacks(sender);
        return Response.ok(fd).build();
    }


}