package ro.endava.bestmarathon.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ro.endava.bestmarathon.domain.QueryResult;
import ro.endava.bestmarathon.domain.TrackTVEntry;
import ro.endava.bestmarathon.http.HttpRequest;
import ro.endava.bestmarathon.http.HttpResponse;
import ro.endava.bestmarathon.http.HttpStatus;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by cosmin on 3/25/14.
 * Service that handles the requests and build the appropriate response
 */
public class ApplicationService {

    public static final String VERSION = "HTTP/1.1";
    public static final String TRACKTV_PATH = "/tracktv";
    public static final String CHARSET = "utf-8";
    public static final String REGEX = "^query=.{2,}";

    private ObjectMapper objectMapper;

    public ApplicationService() {
        objectMapper = new ObjectMapper();
    }

    /**
     * Builds a @see HttpResponse object based on the request
     *
     * @param httpRequest
     * @return
     */
    public HttpResponse buildHttpResponse(HttpRequest httpRequest) throws IOException, URISyntaxException {

        HttpStatus status = getHttpStatus(httpRequest);
        List<String> headers = buildHeaders(httpRequest.getVersion(), status);
        QueryService queryService = new QueryService();
        byte[] body;
        switch (status) {
            case _200:
                String query = URLDecoder.decode(new URI(httpRequest.getUri()).getQuery(), CHARSET);
                Set<TrackTVEntry> querySet = queryService.findByQuery(query.substring(6));
                QueryResult queryResult = new QueryResult(querySet, querySet.size());
                body = objectMapper.writeValueAsString(queryResult).getBytes();
                break;
            case _400:
                body = buildBadRequestBody().getBytes();
                break;
            case _404:
                body = buildNotFoundBody().getBytes();
                break;
            default:
                body = buildNotSupportedBody().getBytes();
                break;
        }

        return new HttpResponse(headers, body);
    }

    private HttpStatus getHttpStatus(HttpRequest httpRequest) throws URISyntaxException, UnsupportedEncodingException {
        HttpStatus result;
        switch (httpRequest.getHttpMethod()) {
            case GET:
                URI uri = new URI(httpRequest.getUri());
                String path = uri.getPath();
                if (path != null && TRACKTV_PATH.equals(path)) {
                    String uriQuery = uri.getQuery();
                    if (uriQuery != null) {
                        String query = URLDecoder.decode(uriQuery, CHARSET);
                        if (!query.matches(REGEX)) {
                            result = HttpStatus._400;//BAD_REQUEST;
                        } else {
                            result = HttpStatus._200;//OK;
                        }
                    } else {
                        result = HttpStatus._400;//BAD_REQUEST;
                    }
                } else {
                    result = HttpStatus._404;//NOT_FOUND;
                }
                break;
            case UNRECOGNIZED:
                result = HttpStatus._400;//BAD_REQUEST;
                break;
            default:
                result = HttpStatus._501;//NOT_SUPPORTED;
                break;
        }
        return result;
    }

    public HttpResponse buildInternalServerErrorResponse() throws JsonProcessingException {
        List<String> headers = buildHeaders(VERSION, HttpStatus._500);
        byte[] body = buildInternalServerErrorBody().getBytes();
        return new HttpResponse(headers, body);
    }

    public HttpResponse buildServiceUnavailableResponse() throws JsonProcessingException {
        List<String> headers = buildHeaders(VERSION, HttpStatus._503);
        byte[] body = buildResourceUnavailableBody().getBytes();
        return new HttpResponse(headers, body);
    }

    private List<String> buildHeaders(String version, HttpStatus httpStatus) {
        List<String> headers = new ArrayList<String>();
        headers.add(version + " " + httpStatus.toString());
        headers.add("Connection: keep-alive");
        headers.add("Server: SynchronousWebServer");
        headers.add("Content-Type: application/json; charset=utf-8");
        return headers;
    }

    private String buildUnrecognizedBody() throws JsonProcessingException {
        return objectMapper.writeValueAsString("You, nasty developer! We do not " +
                "recognize your request method. Please use GET method.");
    }

    private String buildNotSupportedBody() throws JsonProcessingException {
        return objectMapper.writeValueAsString("You, " +
                "nasty developer! We currently do not support your  request method. Please use GET method.");
    }

    private String buildInternalServerErrorBody() throws JsonProcessingException {
        return objectMapper.writeValueAsString("Oups, there seems to be an error an our side." +
                " We are deeply sorry.");
    }

    private String buildResourceUnavailableBody() throws JsonProcessingException {
        return objectMapper.writeValueAsString("You, nasty developer! You know this server has a" +
                " limited number of connections.");
    }

    private String buildBadRequestBody() throws JsonProcessingException {
        return objectMapper.writeValueAsString("You, nasty developer! Your request does not respect" +
                " the right format.");
    }

    private String buildNotFoundBody() throws JsonProcessingException {
        return objectMapper.writeValueAsString("We couldn't connect to the specified url.");
    }

}
