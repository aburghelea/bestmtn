package com.endava.resource;

import com.codahale.metrics.annotation.Timed;
import com.endava.domain.IMDBEntry;
import com.endava.domain.IMDBSearchRef;
import com.endava.service.IMDBService;
import com.wordnik.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Ionuț Păduraru
 */
@Component
@Path("/")
@Api(value = "/", description = "Endpoints for retrieving IMDB search results")
@Produces("application/json")
public class IMDBResource {

    private long seed = System.currentTimeMillis();
    private AtomicInteger numberGenerator = new AtomicInteger(1);
    private Map<String, String> requestMap = Collections.synchronizedMap(new HashMap<String, String>());

    @Autowired
    private IMDBService queryService;

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
            @PathParam("query") String query) {

        String ref = seed + "_" + numberGenerator.incrementAndGet();
        requestMap.put(ref, query);
        return new IMDBSearchRef("results/" + ref);
    }

    @GET
    @Path("results/{searchReference}")
    @Produces("application/json")
    @Timed(name="RESULTS")
    public List<IMDBEntry> results(@PathParam("searchReference") String searchReference) {
        String query = requestMap.get(searchReference);
        return queryService.find(query);
    }
}
