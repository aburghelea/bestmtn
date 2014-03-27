package ro.endava.bestmarathon.http;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cosmin on 3/16/14.
 */
public class HttpRequest {

    private List<String> headers = new ArrayList<String>();

    private HttpMethod httpMethod;

    private String uri;

    private String version;

    public HttpRequest(List<String> headers, HttpMethod httpMethod, String uri, String version) {
        this.headers = headers;
        this.httpMethod = httpMethod;
        this.uri = uri;
        this.version = version;
    }

    public List<String> getHeaders() {
        return headers;
    }

    public HttpMethod getHttpMethod() {
        return httpMethod;
    }

    public String getUri() {
        return uri;
    }

    public String getVersion() {
        return version;
    }

}
