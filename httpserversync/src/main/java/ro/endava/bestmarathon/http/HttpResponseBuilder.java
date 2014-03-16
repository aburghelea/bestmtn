package ro.endava.bestmarathon.http;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cosmin on 3/16/14.
 */
public class HttpResponseBuilder {

    public static final String VERSION = "HTTP/1.1";

    public static HttpResponse buildHttpResponse(HttpRequest httpRequest){

        List<String> headers;
        byte[] body;

        switch (httpRequest.getHttpMethod()){
            case GET:
                headers = buildHeaders(httpRequest.getVersion(), HttpStatus._200);
                body = buildDummyBody().getBytes();
                break;
            case UNRECOGNIZED:
                headers = buildHeaders(httpRequest.getVersion(), HttpStatus._400);
                body = buildUnrecognizedBody().getBytes();
                break;
            default:
                headers = buildHeaders(httpRequest.getVersion(), HttpStatus._501);
                body = buildNotSupportedBody().getBytes();
                break;
        }

        return new HttpResponse(headers, body);
    }

    public static HttpResponse buildInternalServerError(){
        List<String> headers = buildHeaders(VERSION, HttpStatus._500);
        byte[] body = buildInternalServerErrorBody().getBytes();
        return new HttpResponse(headers, body);
    }

    public static HttpResponse buildServiceUnavailable(){
        List<String> headers = buildHeaders(VERSION, HttpStatus._503);
        byte[] body = buildResourceUnavailableBody().getBytes();
        return new HttpResponse(headers, body);
    }

    private static List<String> buildHeaders(String version, HttpStatus httpStatus){
        List<String> headers = new ArrayList<String>();
        headers.add(version + " " + httpStatus.toString());
        headers.add("Connection: close");
        headers.add("Server: SynchronousWebServer");
        headers.add("Content-Type: application/json");
        return headers;
    }

    private static String buildDummyBody(){
        return "Hello, World!";
    }

    private static String buildUnrecognizedBody(){
        return "You, nasty developer! We do not recognize your request method. Please use GET method.";
    }

    private static String buildNotSupportedBody(){
        return "You, nasty developer! We currently do not support your  request method. Please use GET method.";
    }

    private static String buildInternalServerErrorBody(){
        return "Oups, there seems to be an error an our side. We are deeply sorry.";
    }

    private static String buildResourceUnavailableBody(){
        return "You, nasty developer! You know this server has a limited number of connections.";
    }
}
