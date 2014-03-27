package ro.endava.bestmarathon.http;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cosmin on 3/16/14.
 */
public class HttpResponse {

    private List<String> headers = new ArrayList<String>();

    private byte[] body;

    public HttpResponse(List<String> headers, byte[] body) {
        this.headers = headers;
        this.body = body;
    }

    public List<String> getHeaders() {
        return headers;
    }

    public byte[] getBody() {
        return body;
    }
}
