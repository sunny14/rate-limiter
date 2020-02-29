package sunny14.ratelimiter.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import sunny14.ratelimiter.entity.UrlRecord;
import sunny14.ratelimiter.repo.UrlRepo;
import sunny14.ratelimiter.service.Hasher;
import sunny14.ratelimiter.service.RateLimiter;
import sunny14.ratelimiter.service.exceptions.HasherException;
import sunny14.ratelimiter.service.exceptions.RateLimiterException;

import java.util.Optional;

public class RateLimiterImpl implements RateLimiter {

    private static Logger log = LoggerFactory.getLogger("RateLimiterImpl");

    @Autowired
    private Hasher hasher;

    @Autowired
    private UrlRepo repo;

    private Long ttl, threshold;



    public RateLimiterImpl(Long ttl, Long threshold)    {
        this.ttl = ttl;
        this.threshold = threshold;
        log.info("creating rate limiter with ttl="+ttl+", threshold="+threshold);
    }

    @Override
    public boolean isBlocked(String url, Long incomeTs) throws RateLimiterException {

        String hashedUrl;

        try {
            hashedUrl = hasher.hash(url);
        } catch (HasherException e) {
            throw new RateLimiterException(e.getCause());
        }

        Optional op = repo.findById(hashedUrl);
        if (op.isPresent()) {
            UrlRecord rec = (UrlRecord) op.get();
            boolean isInc = rec.inc(incomeTs, ttl, threshold);
            repo.save(rec);

            return !isInc;
        }
        else {
            UrlRecord rec = new UrlRecord(hashedUrl, incomeTs);
            repo.save(rec);
        }

        return true;
    }

}
