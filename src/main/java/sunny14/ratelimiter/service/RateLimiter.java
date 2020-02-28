package sunny14.ratelimiter.service;

public interface RateLimiter {

    boolean isBlocked(String url);
}
