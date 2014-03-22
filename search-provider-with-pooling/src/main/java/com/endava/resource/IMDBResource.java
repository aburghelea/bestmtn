package com.endava.resource;

import com.codahale.metrics.annotation.Timed;
import com.endava.configuration.IMDBSearchEngineConfig;
import com.endava.domain.ErrorResponse;
import com.endava.domain.IMDBEntry;
import com.endava.domain.IMDBSearchRef;
import com.endava.repository.BlackListRepository;
import com.endava.service.IMDBService;
import com.endava.service.SpamService;
import com.endava.service.TimeService;
import com.google.common.base.Ticker;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.wordnik.swagger.annotations.Api;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Ionuț Păduraru
 */
@Component
@Path("/")
@Api(value = "/", description = "Endpoints for retrieving IMDB search results")
@Produces("application/json")
public class IMDBResource implements InitializingBean {

    private long seed = System.currentTimeMillis();
    private AtomicInteger numberGenerator = new AtomicInteger(1);
    private Cache<String, QueryInfo> requestMap;

    @Override
    public void afterPropertiesSet() throws Exception {
        requestMap = CacheBuilder.newBuilder().ticker(ticker).maximumSize(200000).expireAfterWrite(5, TimeUnit.MINUTES).build();
    }

    class QueryInfo {
        String query;
        Long readyTimestamp;

        QueryInfo(String query, Long readyTimestamp) {
            this.query = query;
            this.readyTimestamp = readyTimestamp;
        }
    }

    @Autowired
    private IMDBService queryService;
    @Autowired
    private SpamService spamService;
    @Autowired
    private IMDBSearchEngineConfig config;
    @Autowired
    private Ticker ticker;
    @Autowired
    private TimeService timeService;
    private Random random = new Random(System.currentTimeMillis());

    @GET
    @Path("movies/{query}")
    @Produces("application/json")
//    @ApiOperation(
//            value = "Simple search",
//            notes = "Only the title is considered",
//            response = List.class,
//            responseContainer = "com.endava.domain.IMDBEntry",
//            produces = "application/json"
//    )
//    @ApiResponses({
//            @ApiResponse(code = 500, message = "The search cannot be performed at the moment.")
//    })
    @Timed(name="FIND")
    public IMDBSearchRef find(
//            @ApiParam(value = "The search criteria to use", required = true)
            @PathParam("query") String query, @Context HttpServletRequest request) {

        if (timeService.currentTimeMillis() % config.getFailureSeed() == 0) {
            throw new WebApplicationException(Response.status(Response.Status.SERVICE_UNAVAILABLE)
                   .entity(new ErrorResponse("let's just say we're too busy at the moment")).type(MediaType.APPLICATION_JSON).build());
        }
        String ref = seed + "_" + numberGenerator.incrementAndGet();
        requestMap.put(ref, new QueryInfo(query, timeService.currentTimeMillis() + delay()));
        Response response = Response.
                status(Response.Status.SEE_OTHER).
                header("Location", "http://localhost:" + request.getLocalPort() + "/results/" + ref).
                entity(new IMDBSearchRef("results/" + ref)).
                type(MediaType.APPLICATION_JSON).
                build();
        throw new WebApplicationException(response);
    }

    @GET
    @Path("results/{searchReference}")
    @Produces("application/json")
    @Timed(name="RESULTS")
    public List<IMDBEntry> results(@PathParam("searchReference") String searchReference, @Context HttpServletRequest request) {
        try {
            spamService.checkSpam(request.getRemoteHost());
        } catch (IllegalStateException e) {
            throw new WebApplicationException(Response.status(Response.Status.NOT_ACCEPTABLE)
                    .entity(new ErrorResponse(e.getMessage())).type(MediaType.APPLICATION_JSON).build());
        }
        if (timeService.currentTimeMillis() % config.getFailureSeed() == 0) {
            throw new WebApplicationException(Response.status(Response.Status.SERVICE_UNAVAILABLE)
                    .entity(new ErrorResponse("let's just say we're too busy at the moment")).type(MediaType.APPLICATION_JSON).build());
        }
        QueryInfo queryInfo = requestMap.getIfPresent(searchReference);
        if (queryInfo == null) {
            throw new WebApplicationException(Response.status(Response.Status.GONE)
                    .entity(new ErrorResponse("where did you get that reference from? and when?")).type(MediaType.APPLICATION_JSON).build());
        }
        if (queryInfo.readyTimestamp > timeService.currentTimeMillis()) {
            throw new WebApplicationException(Response.status(Response.Status.ACCEPTED)
                    .entity(new ErrorResponse("we're not ready yet, give us some more time")).type(MediaType.APPLICATION_JSON).build());
        }
        return queryService.find(queryInfo.query);
    }

    private long delay() {
        int delay = config.getMinDelayMs() + random.nextInt(config.getMaxDelayMs() - config.getMinDelayMs());
        System.out.println("delay: " + delay);
        return delay;
    }
}
