package com.endava.bestm.xmltojson;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.json.XML;
import org.junit.Test;
import com.endava.entity.ShowInfo;

import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by iceman on 3/23/14.
 */
public class TvRageToJSON {

    private static final String TVRAGE_FOLDER_PATH = "src/test/resources/tvrageinfo/";

    @Test
    public void testRun() throws Exception {
        StringBuilder sb = new StringBuilder();

        DirectoryStream<Path> dirStream = Files.newDirectoryStream(Paths.get(TVRAGE_FOLDER_PATH));
        for (Path path: dirStream) {
            byte[] bytes = Files.readAllBytes(path);
            String TEST_XML_STRING = Charset.defaultCharset().decode(ByteBuffer.wrap(bytes)).toString();
            JSONObject xmlJSONObj = XML.toJSONObject(TEST_XML_STRING);
            String jsonString = xmlJSONObj.toString();
            jsonString = jsonString.substring(12, jsonString.length()-1);
            sb.append(jsonString).append(",").append('\n');
        }

        String content = "[" + sb.substring(0,sb.length()-2) +"]";

        Files.write(Paths.get(TVRAGE_FOLDER_PATH + "../tvrage.json"), content.toString().getBytes(), StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.CREATE);

    }

    @Test
    public void testReadList() throws Exception {
        List<ShowInfo> data = new ArrayList<ShowInfo>();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
        InputStream jsonStream = getClass().getResourceAsStream("/tvrage.json");

        if (jsonStream != null) {
            try {
                data = objectMapper.readValue(jsonStream, new TypeReference<List<ShowInfo>>() {});
            } finally {
                jsonStream.close();
            }
        }

        System.out.println(data);
    }
}