package com.endava.springboot;

import java.io.IOException;
import java.io.PrintStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * BEST Engineering Marathon 2014
 * Endava Federated Search, Returning(Callback) System
 *
 * @author <a href="mailto:alexandru.burghelea@endava.com">Alexandru BURGHELEA</a>
 * @since 3/25/14
 */
public class TvrageBanner {

    private String text;

    public TvrageBanner() {
        URI banner = null;
        try {
            banner = this.getClass().getResource("/banner.txt").toURI();
            text = Charset.defaultCharset().decode(ByteBuffer.wrap(Files.readAllBytes(Paths.get(banner)))).toString();
        } catch (URISyntaxException e) {
            text = "Best 2014. Tvrage Search Engine";
        } catch (IOException e) {
            text = "Best 2014. Tvrage Search Engine";
        }
    }

    public void print(PrintStream out) {
        out.println(text);
    }
}
