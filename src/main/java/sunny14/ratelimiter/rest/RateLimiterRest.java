package sunny14.ratelimiter.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sunny14.ratelimiter.service.RateLimiter;
import sunny14.ratelimiter.service.exceptions.RateLimiterException;

import javax.websocket.server.PathParam;

@RestController
@RequestMapping(path = "/")
public class RateLimiterRest {

    private static Logger log = LoggerFactory.getLogger("RateLimiterRest");

    @Autowired
    private RateLimiter limiter;

    @GetMapping
    public ResponseEntity<Object> isBlocked(@RequestParam("clientId") String idStr) {

        try {
            long id = Long.parseLong(idStr);
            boolean isBlocked = limiter.isBlocked(id, System.currentTimeMillis());
            if (isBlocked) {
                return ResponseEntity.ok(HttpStatus.SERVICE_UNAVAILABLE);
            }
            return ResponseEntity.ok(HttpStatus.OK);
        }
        catch (NumberFormatException e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
