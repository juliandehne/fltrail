package unipotsdam.gf.modules.peer2peerfeedback.peerfeedback.View;

import unipotsdam.gf.modules.peer2peerfeedback.Category;
import unipotsdam.gf.modules.peer2peerfeedback.peerfeedback.Controller.PeerFeedbackController;
import unipotsdam.gf.modules.peer2peerfeedback.peerfeedback.Model.Peer2PeerFeedback;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.Timestamp;
import java.util.ArrayList;

@Path("/peerfeedback")
@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
@Produces(MediaType.APPLICATION_JSON)
public class PeerFeedbackView {

    /**
     * Returns String
     * @param 'id' ID for peerfeedback
     * @param 'timestamp' time of given feedback
     * @param 'category' category of the document
     * @param 'sender' is feedbacksender
     * @param 'reciever' is feedbackreciever
     * @param 'text' text from editor
     * @param 'filename' name of the document
     * @return name of feedbacksenders or feedbackrecievers
     */
    @POST
    @Path("/save")
    public Response createPeerfeedback(@FormParam("id") String id, @FormParam("timestamp") Timestamp timestamp, @FormParam("category") Category category,
                                       @FormParam("zsm") String sender, @FormParam("reciever") String reciever, @FormParam("text") String text, @FormParam("filename") String filename) {

        System.out.print("VIEW:"+sender);
        Peer2PeerFeedback f = new Peer2PeerFeedback();
        f.setID(id);
        f.setTimestamp(timestamp);
        f.setFeedbackcategory(category);
        f.setFeedbacksender(sender);
        f.setFeedbackreceiver(reciever);
        f.setText(text);
        f.setFilename(filename);
        System.out.print(f);

        PeerFeedbackController controller = new PeerFeedbackController();
        String fp = controller.createPeer2PeerFeedback(f);
        return Response.ok(fp).build();
    }

    /**
     * Returns the sender from feedback
     * @param 'zsm' name and token of student
     * @return name of senders
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/sendedfeedback/{zsm}")
    public Response getPeerfeedback(@PathParam("zsm") String sender){

        PeerFeedbackController controller = new PeerFeedbackController();
        ArrayList<Peer2PeerFeedback> fd = controller.getsendedPeerfedback(sender);
        System.out.print("getallfd:"+fd);
        return Response.ok(fd).build();
    }

    /**
     * Returns the reciever from feedback
     * @param 'zsm' name and token of student
     * @return name of reciever
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/recievedfeedback/{zsm}")
    public Response getRecievedPeerfeedback(@PathParam("zsm") String reciever){

        PeerFeedbackController controller = new PeerFeedbackController();
        ArrayList<Peer2PeerFeedback> rf = controller.getRecievedPeerfeedback(reciever);
        System.out.print("getallfd:"+rf);
        return Response.ok(rf).build();
    }

    /**
     * Returns the names of feedbackreciever
     * @param 'zsm' token of student
     * @return name of feedbackreciever
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/getUsers/{token}")
    public Response getUsers(@PathParam("token") String token){

        PeerFeedbackController controller = new PeerFeedbackController();
        ArrayList<String> users = controller.getUserforFeedback(token);
        System.out.print("usersview:"+users);
        return Response.ok(users).build();
    }

    /**
     * Returns true or false for given feedback
     * @param 'zsm' name and token of student
     * @return true(feedback was given) or false(feedback was not given)
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/checkFeedback/{zsm}")
    public Response checkFeedback(@PathParam("zsm") String checkFeedback){

        System.out.print("sendcf:"+checkFeedback);
        PeerFeedbackController controller = new PeerFeedbackController();
        Boolean check = controller.checkFeedback(checkFeedback);
        System.out.print("checkcf:"+check);
        return Response.ok(check).build();
    }

    /**
     * Returns names of feedbacksender or feedbackreciever
     * @param 'list' name and token of feedbacksenders or feedbackrecievers
     * @return name of feedbacksenders or feedbackrecievers
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/getSender/{list}")
    public Response getSender(@PathParam("list") String token){

        System.out.print("sendcf:"+token);
        PeerFeedbackController controller = new PeerFeedbackController();
        ArrayList<String> name = controller.getSender(token);
        System.out.print("checkcf:"+name);
        return Response.ok(name).build();
    }

    /**
     * Returns feedbacks
     * @param 'student' is feedbackreciever and 'sender' is feedbacksender
     * @return feedbacks
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/getfeedbackbysender/{student}/{sender}")
    public Response getFeedbackBySender(@PathParam("student") String reciever, @PathParam("sender") String sender){

        System.out.print("fb:"+reciever+sender);
        PeerFeedbackController controller = new PeerFeedbackController();
        ArrayList<Peer2PeerFeedback> fb = controller.getFeedbacksBySender(reciever, sender);
        System.out.print("fb:"+fb);
        return Response.ok(fb).build();
    }
}
