package com.endava;

import com.endava.configuration.IMDBSearchEngineConfig;
import com.endava.domain.IMDBEntry;
import com.endava.service.TimeService;
import com.endava.test.IMDBSearchTestEngine;
import com.google.common.io.Resources;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.client.impl.ClientRequestImpl;
import io.dropwizard.testing.junit.DropwizardAppRule;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.mockito.Mockito;

import javax.servlet.ServletException;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;

/**
 * @author Ionuț Păduraru
 */
public class IMDBSearchEngineTest {

    @ClassRule
	public static final DropwizardAppRule<IMDBSearchEngineConfig> RULE = new DropwizardAppRule<>(IMDBSearchTestEngine.class, resourceFilePath("imdb-test-config.yml"));

    private TimeService timeService;

    public static String resourceFilePath(String resourceClassPathLocation) {
        try {
            return new File(Resources.getResource(resourceClassPathLocation).toURI()).getAbsolutePath();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Before
    public void setUp() throws Exception {
        IMDBSearchTestEngine application = RULE.getApplication();
        timeService = application.applicationContext().getBean(TimeService.class);
        Mockito.reset(timeService);
    }

    // ensure 503 errors
    @Test
    public void serviceUnavailable() throws IOException, ServletException {
        WebResource resource = new Client().resource("http://localhost:" + RULE.getLocalPort() + "/movies/love");
        ClientRequestImpl get = new ClientRequestImpl(resource.getURI(), "GET");
        ClientResponse response = resource.getHeadHandler().handle(get);
        assertEquals(503, response.getStatus());
    }

    // ensure we get back a 303 (search reference)
    @Test
    public void find() throws IOException, ServletException {
        when(timeService.currentTimeMillis()).thenReturn(121212L);
        Client client = new Client();
        client.setFollowRedirects(false);
        WebResource resource = client.resource("http://localhost:" + RULE.getLocalPort() + "/movies/love");
        ClientRequestImpl get = new ClientRequestImpl(resource.getURI(), "GET");
        ClientResponse response = resource.getHeadHandler().handle(get);
        assertEquals(303, response.getStatus());
        String location = response.getLocation().toString();
        Assert.assertTrue(location, location.startsWith("http://localhost:" + RULE.getLocalPort() + "/results/"));
    }

    // ensure the results are not available immediately
    @Test
    public void findResultsDelayed() throws IOException, ServletException {
        when(timeService.currentTimeMillis()).thenReturn(1L);
        Client client = new Client();
        client.setFollowRedirects(false);
        WebResource resource = client.resource("http://localhost:" + RULE.getLocalPort() + "/movies/love");
        ClientRequestImpl get = new ClientRequestImpl(resource.getURI(), "GET");
        ClientResponse response = resource.getHeadHandler().handle(get);
        assertEquals(303, response.getStatus());
        String location = response.getLocation().toString();
        Assert.assertTrue(location, location.startsWith("http://localhost:" + RULE.getLocalPort() + "/results/"));

        resource = client.resource(location);
        get = new ClientRequestImpl(resource.getURI(), "GET");
        response = resource.getHeadHandler().handle(get);
        assertEquals(202, response.getStatus());
    }

    // ensure the result are available after a delay
    @Test
    public void findResults() throws IOException, ServletException {
        when(timeService.currentTimeMillis()).thenReturn(1L);
        Client client = new Client();
        client.setFollowRedirects(false);
        WebResource resource = client.resource("http://localhost:" + RULE.getLocalPort() + "/movies/love");
        ClientRequestImpl get = new ClientRequestImpl(resource.getURI(), "GET");
        ClientResponse response = resource.getHeadHandler().handle(get);
        assertEquals(303, response.getStatus());
        String location = response.getLocation().toString();
        Assert.assertTrue(location, location.startsWith("http://localhost:" + RULE.getLocalPort() + "/results/"));

        reset(timeService);
        when(timeService.currentTimeMillis()).thenReturn(1000L);
        resource = client.resource(location);
        get = new ClientRequestImpl(resource.getURI(), "GET");
        response = resource.getHeadHandler().handle(get);
        assertEquals(200, response.getStatus());

        List<IMDBEntry> results = response.getEntity(new GenericType<List<IMDBEntry>>() {});
        assertEquals("number of results", 3, results.size());
        assertEquals("Love Hina", results.get(0).getTitle());
        assertEquals("Love Bites", results.get(1).getTitle());
        assertEquals("Mad Love", results.get(2).getTitle());
    }

    // ensure the result are not available forever
    @Test
    public void resultsAreGone() throws IOException, ServletException {
        when(timeService.currentTimeMillis()).thenReturn(1L);
        Client client = new Client();
        client.setFollowRedirects(false);
        WebResource resource = client.resource("http://localhost:" + RULE.getLocalPort() + "/movies/love");
        ClientRequestImpl get = new ClientRequestImpl(resource.getURI(), "GET");
        ClientResponse response = resource.getHeadHandler().handle(get);
        assertEquals(303, response.getStatus());
        String location = response.getLocation().toString();
        Assert.assertTrue(location, location.startsWith("http://localhost:" + RULE.getLocalPort() + "/results/"));

        reset(timeService);
        when(timeService.currentTimeMillis()).thenReturn(TimeUnit.MINUTES.toMillis(30)); // 30 minutes
        resource = client.resource(location);
        get = new ClientRequestImpl(resource.getURI(), "GET");
        response = resource.getHeadHandler().handle(get);
        assertEquals(410, response.getStatus());
    }

}
