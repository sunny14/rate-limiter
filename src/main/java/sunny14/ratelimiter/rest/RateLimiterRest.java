package sunny14.ratelimiter.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sunny14.ratelimiter.service.RateLimiter;
import sunny14.ratelimiter.service.exceptions.RateLimiterException;

@RestController
@RequestMapping(path = "/")
public class RateLimiterRest {

    private static Logger log = LoggerFactory.getLogger("RateLimiterRest");

    @Autowired
    private RateLimiter limiter;

    @PostMapping(path = "/report", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Object> isBlocked(@RequestBody String url) {

        try {
            boolean isBlocked = limiter.isBlocked(url, System.currentTimeMillis());
            return ResponseEntity.ok(new Pair(isBlocked));
        } catch (RateLimiterException e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    private class Pair  {
        boolean block;
        public Pair(boolean bl) {
            this.block = bl;
        }

        public boolean getBlock()   {
            return block;
        }
    }


}
