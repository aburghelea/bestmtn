package ro.endava.bestmarathon.utils;

import ro.endava.bestmarathon.http.HttpMethod;
import ro.endava.bestmarathon.http.HttpRequest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by cosmin on 3/25/14.
 */
public class HttpRequestBuilder {

    public static HttpRequest buildHttpRequest(InputStream is) throws IOException {

        HttpMethod httpMethod;
        List<String> headers = new ArrayList<String>();
        String uri;
        String version;

        BufferedReader reader = new BufferedReader(new InputStreamReader(is));

        String str = reader.readLine();
        String[] split = str.split("\\s+");
        try {
            httpMethod = HttpMethod.valueOf(split[0]);
        } catch (Exception e) {
            httpMethod = HttpMethod.UNRECOGNIZED;
        }
        uri = split[1];
        version = split[2];

        while (!str.equals("")) {
            str = reader.readLine();
            headers.add(str);
        }

        return new HttpRequest(headers, httpMethod, uri, version);
    }
}
