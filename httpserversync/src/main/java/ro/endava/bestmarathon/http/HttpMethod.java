package ro.endava.bestmarathon.http;

/**
 * Created by cosmin on 3/16/14.
 */
public enum HttpMethod {
    GET("GET"),
    HEAD("HEAD"),
    POST("POST"),
    PUT("PUT"),
    DELETE("DELETE"),
    UNRECOGNIZED(null);

    private final String method;

    HttpMethod(String method) {
        this.method = method;
    }
}

