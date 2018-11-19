package unipotsdam.gf.modules.communication.service;

import io.github.openunirest.http.Unirest;
import io.github.openunirest.mappers.JacksonObjectMapper;
import io.github.openunirest.request.GetRequest;
import io.github.openunirest.request.HttpRequestWithBody;

import javax.annotation.ManagedBean;
import javax.annotation.Resource;
import javax.inject.Singleton;

@ManagedBean
@Resource
@Singleton
public class UnirestService {

    private static boolean isInitialized = false;

    public UnirestService() {
        // has to be set for application
        if (!isInitialized) {
            Unirest.setObjectMapper(new JacksonObjectMapper());
            Unirest.setDefaultHeader("Content-Type", "application/json");
            isInitialized = true;
        }
    }

    public GetRequest get(String url) {
        return Unirest.get(url);
    }

    public HttpRequestWithBody post(String url) {
        return Unirest.post(url);
    }

    public HttpRequestWithBody delete(String url) {
        return Unirest.delete(url);
    }

    public HttpRequestWithBody put(String url) {
        return Unirest.put(url);
    }
}
