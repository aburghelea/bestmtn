package com.endava.resource;

import com.codahale.metrics.annotation.Timed;
import com.endava.configuration.IMDBSearchEngineConfig;
import com.endava.domain.ErrorResponse;
import com.endava.domain.IMDBEntry;
import com.endava.domain.IMDBSearchRef;
import com.endava.service.IMDBService;
import com.endava.service.SpamService;
import com.endava.service.TimeService;
import com.google.common.base.Ticker;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.wordnik.swagger.annotations.*;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Ionuț Păduraru
 */
@Component
@Path("/movies")
@Api(value = "/movies", description = "Endpoints for retrieving IMDB search results")
@Produces("application/json" + ";charset=utf-8")
public class IMDBResource implements InitializingBean {

    private AtomicInteger numberGenerator = new AtomicInteger(1);
    private Cache<String, QueryInfo> requestMap;

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

    @Override
    public void afterPropertiesSet() throws Exception {
        requestMap = CacheBuilder.newBuilder()
                .ticker(ticker)
                .maximumSize(config.getResponsesMaxCnt())
                .expireAfterWrite(config.getResponseTTLMin(), TimeUnit.MINUTES)
                .build();
    }

    @GET
    @Path("/{query}")
    @ApiOperation(
            value = "Simple IMDB search endpoint",
            notes = "Specify one or more keywords",
            response = IMDBSearchRef.class,
            produces = "application/json",
            nickname = "search"
    )
    @ApiResponses({
            @ApiResponse(code = 303, message = "Use the 'Location' header to get the search reference"),
            @ApiResponse(code = 406, message = "The request is not acceptable (ex: the query is too long ot too short)"),
            @ApiResponse(code = 500, message = "The search cannot be performed at the moment.")
    })
    @Timed(name="FIND")
    public IMDBSearchRef find(
            @ApiParam(value = "The search criteria to use", required = true)
            @PathParam("query")
            String query, @Context HttpServletRequest request) {

        if (timeService.currentTimeMillis() % config.getFailureSeed() == 0) {
            throw new WebApplicationException(Response.status(Response.Status.SERVICE_UNAVAILABLE)
                   .entity(new ErrorResponse("let's just say we're too busy at the moment")).type(MediaType.APPLICATION_JSON).build());
        }
        if (!StringUtils.hasText(query) || query.length() < 3 || query.length() > 200) {
            throw new WebApplicationException(Response.status(Response.Status.NOT_ACCEPTABLE)
                    .entity(new ErrorResponse("Invalid query")).type(MediaType.APPLICATION_JSON).build());
        }
        String ref = String.valueOf(numberGenerator.incrementAndGet());
        requestMap.put(ref, new QueryInfo(query, timeService.currentTimeMillis() + delay()));
        Response response = Response.
                status(Response.Status.SEE_OTHER).
                header("Location", "http://" + request.getServerName() + ":" + request.getServerPort() + "/movies/results/" + ref).
                entity(new IMDBSearchRef(ref)).
                type(MediaType.APPLICATION_JSON).
                build();
        throw new WebApplicationException(response);
    }

    @GET
    @Path("/results/{searchReference}")
    @ApiOperation(
            value = "Endpoint to the the search results",
            notes = "You need a search reference for this; Use the 'search' endpoint to obtain a reference",
            response = IMDBEntry.class,
            responseContainer = "List",
            produces = "application/json"
    )
    @ApiResponses({
//            @ApiResponse(code = 406, message = "Too many requests; cool down"),
            @ApiResponse(code = 200, message = "Search results are available."),
            @ApiResponse(code = 202, message = "The results are not yet available. Retry later"),
            @ApiResponse(code = 410, message = "The results are no longer available."),
            @ApiResponse(code = 500, message = "The search cannot be performed at the moment.")
    })
    @Timed(name="RESULTS")
    public List<IMDBEntry> results(@PathParam("searchReference")
                                   @ApiParam(value = "The search reference", required = true)
                                   String searchReference, @Context HttpServletRequest request) {
        try {
            spamService.checkSpam(request.getRemoteHost());
        } catch (IllegalStateException e) {
            throw new WebApplicationException(Response.status(Response.Status.NOT_ACCEPTABLE)
                    .entity(new ErrorResponse(e.getMessage())).type(MediaType.APPLICATION_JSON).build());
        }
        if (config.getFailureSeed() > 0 && timeService.currentTimeMillis() % config.getFailureSeed() == 0) {
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
        return delay;
    }
}
