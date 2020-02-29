package sunny14.ratelimiter.service;

import sunny14.ratelimiter.service.exceptions.RateLimiterException;

public interface RateLimiter {

    boolean isBlocked(String url, Long incomeTs) throws RateLimiterException;
}
