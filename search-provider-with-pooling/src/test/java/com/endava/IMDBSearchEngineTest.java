package com.endava;

import com.endava.configuration.IMDBSearchEngineConfig;
import com.endava.domain.IMDBEntry;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.io.Resources;
import com.sun.jersey.api.client.Client;
import io.dropwizard.testing.junit.DropwizardAppRule;
import org.junit.ClassRule;
import org.junit.Test;

import javax.servlet.ServletException;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

/**
 * @author Ionuț Păduraru
 */
public class IMDBSearchEngineTest {

    @ClassRule
	public static final DropwizardAppRule<IMDBSearchEngineConfig> RULE = new DropwizardAppRule<>(IMDBSearchEngine.class, resourceFilePath("imdb-test-config.yml"));

    public static String resourceFilePath(String resourceClassPathLocation) {
        try {
            return new File(Resources.getResource(resourceClassPathLocation).toURI()).getAbsolutePath();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void find() throws IOException, ServletException {
        String content = new Client().resource("http://localhost:" + RULE.getLocalPort() + "/movies/love").get(String.class);

        ObjectMapper objectMapper = new ObjectMapper();
        Map refs = objectMapper.readValue(content.getBytes("UTF-8"), Map.class);
        Object searchRef = refs.get("path");

        assertTrue(refs.containsKey("path"));

        content = new Client().resource("http://localhost:" + RULE.getLocalPort() + "/" + searchRef).get(String.class);
        List<IMDBEntry> results = objectMapper.readValue(content.getBytes("UTF-8"), new TypeReference<List<IMDBEntry>>() {
        });
        assertEquals("number of results", 3, results.size());
        assertEquals("Love Hina", results.get(0).getTitle());
        assertEquals("Love Bites", results.get(1).getTitle());
        assertEquals("Mad Love", results.get(2).getTitle());
    }

}
