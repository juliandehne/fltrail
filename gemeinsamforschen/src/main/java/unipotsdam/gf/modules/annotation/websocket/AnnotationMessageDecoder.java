package unipotsdam.gf.modules.annotation.websocket;

import com.google.gson.Gson;
import unipotsdam.gf.modules.annotation.model.AnnotationMessage;

import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;

public class AnnotationMessageDecoder implements Decoder.Text<AnnotationMessage> {

    public static Gson gson = new Gson();

    @Override
    public AnnotationMessage decode(String s) throws DecodeException {
        AnnotationMessage annotationMessage = gson.fromJson(s, AnnotationMessage.class);
        return annotationMessage;
    }

    @Override
    public boolean willDecode(String s) {
        return (null != s);
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
