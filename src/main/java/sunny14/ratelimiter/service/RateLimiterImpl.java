package sunny14.ratelimiter.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RateLimiterImpl implements RateLimiter {


    private Logger log = LoggerFactory.getLogger(this.getClass().getName());


    private Long ttl, threshold;



    public RateLimiterImpl(Long ttl, Long threshold)    {
        this.ttl = ttl;
        this.threshold = threshold;
        log.info("creating rate limiter with ttl="+ttl+", threshold="+threshold);
    }

    @Override
    public boolean isBlocked(String url) {
        return true;
    }
}
