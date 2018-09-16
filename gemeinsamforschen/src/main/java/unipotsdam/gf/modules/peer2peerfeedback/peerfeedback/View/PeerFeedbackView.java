package unipotsdam.gf.modules.peer2peerfeedback.peerfeedback.View;

import unipotsdam.gf.modules.peer2peerfeedback.Category;
import unipotsdam.gf.modules.peer2peerfeedback.peerfeedback.Model.Peer2PeerFeedback;
import unipotsdam.gf.modules.peer2peerfeedback.peerfeedback.Controller.PeerFeedbackController;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;

@Path("/peerfeedback")
@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
@Produces(MediaType.APPLICATION_JSON)
public class PeerFeedbackView {


    @POST
    @Path("/save")
    public Response createPeerfeedback( @FormParam("id") String id, @FormParam("timestamp") Long timestamp, @FormParam("category") Category category,
                                        @FormParam("student") String sender, @FormParam("reciever") String reciever,  @FormParam("text") String text, @FormParam("filename") String filename) {

        System.out.print("id="+id +"time="+timestamp +"cat="+category +
                "sender="+sender +"reciever="+reciever +"text="+text +"fn"+filename);

        //Peer2PeerFeedback feedback = new Peer2PeerFeedback(id, timestamp, Category.TITEL, sender, text, reciever, filename);
        Peer2PeerFeedback f = new Peer2PeerFeedback();
        f.setID(id);
        f.setTimestamp(33333);
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
        System.out.print(fd);
        return Response.ok(fd).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/getUsers")
    public Response getUsers(){

        PeerFeedbackController controller = new PeerFeedbackController();
        ArrayList<String> users = controller.getUserforFeedback();
        System.out.print(users);
        return Response.ok(users).build();
    }

    @POST
    //@Produces(MediaType.APPLICATION_JSON)
    @Path("{getToken}")
    public Response getToken(@PathParam("getToken") String user){

        PeerFeedbackController controller = new PeerFeedbackController();
        controller.getTokenforFeedback(user);
        System.out.print(user);
        return Response.ok().build();
    }


}
