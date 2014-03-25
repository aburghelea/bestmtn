package ro.endava.bestmarathon.utils;

import ro.endava.bestmarathon.http.HttpResponse;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

/**
 * Created by cosmin on 3/25/14.
 */
public class HttpResponseWriter {

    /**
     * Writes backs the response on socket
     *
     * @param socket
     * @param httpResponse
     * @throws java.io.IOException
     */
    public static void writeOnSocketAndClose(Socket socket, HttpResponse httpResponse) throws IOException {
        write(socket.getOutputStream(), httpResponse);
        socket.close();
    }

    public static void write(OutputStream os, HttpResponse httpResponse) throws IOException {
        DataOutputStream output = new DataOutputStream(os);
        for (String header : httpResponse.getHeaders()) {
            output.writeBytes(header + "\r\n");
        }
        output.writeBytes("\r\n");
        if (httpResponse.getBody() != null) {
            output.write(httpResponse.getBody());
        }
        output.writeBytes("\r\n");
        output.flush();
    }
}
