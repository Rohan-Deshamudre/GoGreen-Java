package server;

import communication.clientMessage.AddActivityRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import static server.ServerApplication.checkLoginData;

@Controller
public class ActivityController {
    /**
     * Adds a page /activity which handles add/remove activity requests.
     */
    @RequestMapping(value = "/activity",
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public ResponseEntity<String> mirror(@RequestBody AddActivityRequest act) {
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
