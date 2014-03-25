package com.endava.controller;

import com.mangofactory.swagger.annotations.ApiError;
import com.mangofactory.swagger.annotations.ApiErrors;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.endava.response.SimpleResponse;
import com.endava.entity.ShowInfo;
import com.endava.exception.InvaliCallBackException;
import com.endava.exception.RandomServerException;
import com.endava.service.ReturningService;

import java.util.List;

/**
 * BEST Engineering Marathon 2014
 * Endava Federated Search, Returning(Callback) System
 *
 * @author <a href="mailto:alexandru.burghelea@endava.com">Alexandru BURGHELEA</a>
 * @since 3/13/14
 */
@Controller
@RequestMapping("/tvrage")
@Api(value = "/tvrage", description = "Endpoints for retrieving TvRage search results")
public class TvRageResourceController {

    private Logger logger = LoggerFactory.getLogger(TvRageResourceController.class);

    @Autowired
    private ReturningService returningService;

    @RequestMapping(value = "/query/{query}", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    @ApiOperation(
            value = "Simple TvRage search endpoint",
            notes = "Specify a keyword and a callback",
            responseClass = "SimpleResponse"
    )
    @ApiErrors(errors = {
            @ApiError(code = 400, reason = "The provided callback is invalid"),
            @ApiError(code = 500, reason = "The server is malfunctioning")
    })
    public ResponseEntity<SimpleResponse> requestInfo(@PathVariable(value = "query") String query, @RequestParam(value = "callback") String callback) {

        ResponseEntity<SimpleResponse> responseEntity;

        try {
            returningService.computeResult(query, callback);
            responseEntity = new ResponseEntity<SimpleResponse>(new SimpleResponse("Your request will be processed. We'll get back to you"), HttpStatus.OK);
        } catch (InvaliCallBackException e) {
            responseEntity = new ResponseEntity<SimpleResponse>(new SimpleResponse(e.getMessage()), HttpStatus.BAD_REQUEST);
        } catch (RandomServerException e) {
            responseEntity = new ResponseEntity<SimpleResponse>(new SimpleResponse(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;

    }

    @RequestMapping(value = "/callback", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(
            value = "Exemple of a valid callback",
            notes = "This is how the callback getting the results can look like",
            responseClass = "SimpleResponse"
    )
    public ResponseEntity<SimpleResponse> dummyCallback(@RequestBody List<ShowInfo> showInfos) {

        SimpleResponse simpleResponse = new SimpleResponse(String.valueOf(showInfos.size()));
        logger.info("i have been called back");
        return new ResponseEntity<SimpleResponse>(simpleResponse, HttpStatus.OK);

    }
}