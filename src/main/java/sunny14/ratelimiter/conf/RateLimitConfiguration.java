package sunny14.ratelimiter.conf;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import sunny14.ratelimiter.service.RateLimiter;
import sunny14.ratelimiter.service.RateLimiterImpl;

@Configuration
public class RateLimitConfiguration {


    @Value("${ttl}")
    private Long ttl;

    @Value("${threshold}")
    private Long threshold;

    @Bean
    public RateLimiter getRateLimiter() {
        return new RateLimiterImpl(ttl, threshold);
    }
}
