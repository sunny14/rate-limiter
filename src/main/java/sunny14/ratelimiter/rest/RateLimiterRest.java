package sunny14.ratelimiter.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sunny14.ratelimiter.service.RateLimiter;

@RestController
@RequestMapping(path = "/report")
public class RateLimiterRest {

    @Autowired
    private RateLimiter limiter;

    @PostMapping(path = "/", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Object> isBlocked(@RequestBody String url) {
        return ResponseEntity.ok(limiter.isBlocked(url));
    }


}
