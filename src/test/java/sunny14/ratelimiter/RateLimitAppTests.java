package sunny14.ratelimiter;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(properties = {"ttl=60000", "threshold=5"})
class RateLimitAppTests {

	@Test
	void contextLoads() {
	}

}
