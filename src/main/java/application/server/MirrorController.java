package application.server;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MirrorController {
    /**
     * Adds a page /test/status which responds an up message.
     */
    @RequestMapping(value = "/test/status",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public ResponseEntity<String> status() {
        return new ResponseEntity<>("{\"status\" : \"UP\"}", HttpStatus.OK);
    }

    /**
     * Adds a page /test/mirror which responds by mirroring the request.
     */
    @RequestMapping(value = "/test/mirror",
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public ResponseEntity<String> mirror(@RequestBody String req) {
        return new ResponseEntity<>(req, HttpStatus.OK);
    }
}
