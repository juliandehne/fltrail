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


    @POST
    @Path("/save")
    public Response createPeerfeedback(@FormParam("id") String id, @FormParam("timestamp") Timestamp timestamp, @FormParam("category") Category category,
                                       @FormParam("student") String sender, @FormParam("reciever") String reciever, @FormParam("text") String text, @FormParam("filename") String filename) {

        //Peer2PeerFeedback feedback = new Peer2PeerFeedback(id, timestamp, Category.TITEL, sender, text, reciever, filename);
        Peer2PeerFeedback f = new Peer2PeerFeedback();
        f.setID(id);
        f.setTimestamp(timestamp);
        f.setFeedbackcategory(category);
        f.setFeedbacksender(sender);
        f.setFeedbackreceiver(reciever);
        f.setText(text);
        f.setFilename(filename);
        System.out.print(f);
        //Peer2PeerFeedback fd = new Peer2PeerFeedback(id="1234", timestamp=null, Category.TITEL, sender="sender", text="test1", reciever="reciever", filename="filename");

        PeerFeedbackController controller = new PeerFeedbackController();
        controller.createPeer2PeerFeedback(f);


        return Response.ok().build(); //feedback

    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{student}")
    public Response getPeerfeedback(@PathParam("student") String sender){

        PeerFeedbackController controller = new PeerFeedbackController();
        ArrayList<Peer2PeerFeedback> fd = controller.getAllFeedbacks(sender);
        System.out.print("getallfd:"+fd);
        return Response.ok(fd).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/getUsers/{token}")
    public Response getUsers(@PathParam("token") String token){

        PeerFeedbackController controller = new PeerFeedbackController();
        ArrayList<String> users = controller.getUserforFeedback(token);
        System.out.print("usersview:"+users);
        return Response.ok(users).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/checkFeedback/{checkFeedback}")
    public Response checkFeedback(@PathParam("checkFeedback") String checkFeedback){

        System.out.print("sendcf:"+checkFeedback);
        PeerFeedbackController controller = new PeerFeedbackController();
        Boolean check = controller.checkFeedback(checkFeedback);
        System.out.print("checkcf:"+check);
        return Response.ok(check).build();
    }

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
