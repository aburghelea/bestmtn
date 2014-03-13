package ro.endava.bestm.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import ro.endava.bestm.dto.SimpleResponse;
import ro.endava.bestm.service.ReturningService;

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

    @RequestMapping("/greeting")
    @ResponseBody
    public ResponseEntity<SimpleResponse> greeting(@RequestParam(value = "query") String query, @RequestParam(value = "callback") String callback) {

        SimpleResponse simpleResponse = new SimpleResponse("Da");
        returningService.computeResult(query, callback);
        return new ResponseEntity<SimpleResponse>(simpleResponse, HttpStatus.OK);

    }

    @RequestMapping("/callback")
    @ResponseBody
    public ResponseEntity<SimpleResponse> greeting(@RequestParam(value = "query") String query) {

        SimpleResponse simpleResponse = new SimpleResponse(query);
        logger.info("i have been called back");
        return new ResponseEntity<SimpleResponse>(simpleResponse, HttpStatus.OK);

    }
}