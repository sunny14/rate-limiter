package sunny14.ratelimiter.service;

import sunny14.ratelimiter.service.exceptions.RateLimiterException;

public interface RateLimiter {

    boolean isBlocked(String url) throws RateLimiterException;
}
