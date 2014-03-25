package ro.endava.bestm.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ro.endava.bestm.dto.SimpleResponse;
import ro.endava.bestm.entity.ShowInfo;
import ro.endava.bestm.exception.InvaliCallBackException;
import ro.endava.bestm.exception.RandomServerException;
import ro.endava.bestm.service.ReturningService;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.HttpMethod;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

/**
 * BEST Engineering Marathon 2014
 * Endava Federated Search, Returning(Callback) System
 *
 * @author <a href="mailto:alexandru.burghelea@endava.com">Alexandru BURGHELEA</a>
 * @since 3/13/14
 */
@Controller
public class EntryPointController {

    private Logger logger = LoggerFactory.getLogger(EntryPointController.class);

    @Autowired
    private ReturningService returningService;

    @RequestMapping(value = "/query/{query}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<SimpleResponse> requestInfo(@PathVariable(value = "query") String query, @RequestParam(value = "callback") String callback) {

        SimpleResponse simpleResponse = new SimpleResponse("Da");
        ResponseEntity<SimpleResponse>responseEntity;

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
    public ResponseEntity<SimpleResponse> dummyCallback(@RequestBody List<ShowInfo> showInfos) {

        SimpleResponse simpleResponse = new SimpleResponse(String.valueOf(showInfos.size()));
        logger.info("i have been called back");
        return new ResponseEntity<SimpleResponse>(simpleResponse, HttpStatus.OK);

    }
}