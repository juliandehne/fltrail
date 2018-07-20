package unipotsdam.gf.modules.annotation.websocket;

import unipotsdam.gf.modules.annotation.model.AnnotationMessage;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.HashMap;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

@ServerEndpoint(value = "/ws/annotation/{targetId}", decoders = AnnotationMessageDecoder.class, encoders = AnnotationMessageEncoder.class)
public class AnnotationWebSocketEndpoint {

    private Session session;
    private static final Set<AnnotationWebSocketEndpoint> endpoints = new CopyOnWriteArraySet<>();
    private static HashMap<String, String> targets = new HashMap<>();

    @OnOpen
    public void onOpen(Session session, @PathParam("targetId") String targetId) throws IOException {
        // initialize session
        this.session = session;
        // save endpoint in set of endpoints
        endpoints.add(this);
        // save mapping of session and target id
        targets.put(session.getId(), targetId);
    }

    @OnMessage
    public void onMessage(Session session, AnnotationMessage annotationMessage) throws IOException, EncodeException {
        annotationMessage.setFrom(targets.get(session.getId()));
        broadcast(annotationMessage);

    }

    @OnClose
    public void onClose(Session session) throws IOException {
        endpoints.remove(this);
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        // todo
    }

    private void broadcast(AnnotationMessage annotationMessage) throws IOException, EncodeException {
        endpoints.forEach(endpoint -> {
            synchronized (endpoint) {
                try {
                    if (targets.get(endpoint.session.getId()).equals(annotationMessage.getFrom())) {
                        endpoint.session.getBasicRemote().sendObject(annotationMessage);
                    }
                }
                catch (IOException | EncodeException e) {
                    e.printStackTrace();
                }
            }
        });
    }

}
