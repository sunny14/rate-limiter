package sunny14.ratelimiter;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import sunny14.ratelimiter.entity.UrlRecord;
import sunny14.ratelimiter.repo.UrlRepo;
import sunny14.ratelimiter.service.Hasher;
import sunny14.ratelimiter.service.RateLimiter;
import sunny14.ratelimiter.service.exceptions.HasherException;
import sunny14.ratelimiter.service.exceptions.RateLimiterException;
import sunny14.ratelimiter.service.impl.RateLimiterImpl;

import java.util.Optional;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(SpringRunner.class)
@TestPropertySource(properties = {"ttl=60000", "threshold=5"})
public class RateLimiterServiceTests {

    private static final String TEST_URL  = "www.anyurl.com";

    private Long ttl = 60000L;
    private Long threshold = 5L;

    @Mock
    private Hasher hasher;

    @Mock
    private UrlRepo repo;

    @InjectMocks
    private RateLimiter limiter = new RateLimiterImpl(ttl, threshold);


    @Test
    public void noMatchTest() throws RateLimiterException {
        when(repo.findById(anyString())).thenReturn(Optional.empty());

        boolean isBlocked = this.limiter.isBlocked(TEST_URL, System.currentTimeMillis());
        assertFalse(isBlocked);
    }

    @Test
    public void matchWithinThresholdTest() throws HasherException, RateLimiterException {

        //create mock record with {ttl-1} timestamps within a threshold
        Long ts = System.currentTimeMillis();
        UrlRecord rec = new UrlRecord(TEST_URL, ts);
        long countToSet = threshold-2L;
        for (int i=0; i<countToSet; i++) {
            rec.inc(ts, ttl, threshold);
        }

        when(repo.findById(any())).thenReturn(Optional.ofNullable(rec));

        boolean isBlocked = this.limiter.isBlocked(TEST_URL, ts);
        assertFalse(isBlocked);
    }

    @Test
    public void matchWithinThresholdOldEntriesTest() throws RateLimiterException {

        //create mock record with ttl-1 timestamps within a threshold + 1 expired timestamp
        Long expiredTs = System.currentTimeMillis()-ttl*2;
        UrlRecord rec = new UrlRecord(TEST_URL, expiredTs);
        Long goodTs = System.currentTimeMillis();
        long countToSet = threshold-1L;
        for (int i=0; i<countToSet; i++) {
            rec.inc(goodTs, ttl, threshold);
        }

        when(repo.findById(any())).thenReturn(Optional.ofNullable(rec));

        boolean isBlocked = this.limiter.isBlocked(TEST_URL, goodTs);
        assertFalse(isBlocked);
    }

    @Test
    public void matchOutOfThresholdTest() throws RateLimiterException {

        //create mock record with ttl+1 timestamps within a threshold
        Long ts = System.currentTimeMillis();
        UrlRecord rec = new UrlRecord(TEST_URL, ts);
        long countToSet = threshold;
        for (int i=0; i<countToSet; i++) {
            rec.inc(ts, ttl, threshold);
        }

        when(repo.findById(any())).thenReturn(Optional.ofNullable(rec));

        boolean isBlocked = this.limiter.isBlocked(TEST_URL, ts);
        assertTrue(isBlocked);
    }
}
