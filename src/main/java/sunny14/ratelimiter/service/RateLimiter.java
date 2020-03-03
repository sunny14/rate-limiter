package sunny14.ratelimiter.service;

import sunny14.ratelimiter.service.exceptions.RateLimiterException;

public interface RateLimiter {

    boolean isBlocked(long url, Long incomeTs);
}
