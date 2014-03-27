package com.endava.springboot;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.io.StringWriter;
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
            InputStream stream = this.getClass().getResourceAsStream("/banner.txt");
            StringWriter writer = new StringWriter();
            IOUtils.copy(stream,writer);
            text = writer.toString();
//            text = Charset.defaultCharset().decode(ByteBuffer.wrap(Files.readAllBytes(Paths.get(banner)))).toString();
        } catch (IOException e) {
            text = "Best 2014. Tvrage Search Engine";
        }
    }

    public void print(PrintStream out) {
        out.println(text);
    }
}
