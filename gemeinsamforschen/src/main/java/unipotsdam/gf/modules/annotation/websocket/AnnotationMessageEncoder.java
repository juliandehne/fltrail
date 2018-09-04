package unipotsdam.gf.modules.annotation.websocket;

import com.google.gson.Gson;
import unipotsdam.gf.modules.annotation.model.AnnotationMessage;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

public class AnnotationMessageEncoder implements Encoder.Text<AnnotationMessage> {

    private static Gson gson = new Gson();

    @Override
    public String encode(AnnotationMessage annotationMessage) throws EncodeException {
        String json = gson.toJson(annotationMessage);
        return json;
    }

    @Override
    public void init(EndpointConfig endpointConfig) {
        // todo
    }

    @Override
    public void destroy() {
        // todo
    }
}
