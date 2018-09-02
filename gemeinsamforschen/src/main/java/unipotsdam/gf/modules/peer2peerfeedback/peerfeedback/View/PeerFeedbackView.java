package unipotsdam.gf.modules.peer2peerfeedback.peerfeedback.View;

import com.sun.tools.xjc.model.Model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import unipotsdam.gf.modules.peer2peerfeedback.Category;
import unipotsdam.gf.modules.peer2peerfeedback.peerfeedback.Model.Peer2PeerFeedback;
import unipotsdam.gf.modules.peer2peerfeedback.peerfeedback.Controller.PeerFeedbackController;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

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


        return Response.ok(feedback).build();

    }


}
