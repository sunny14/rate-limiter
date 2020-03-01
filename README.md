# rate-limiter
Simple rate limiter service in Java + Springboot

# How to run
1. Checkout from GitHub
2. Run class RateLimitApp with following arguments:
--ttl=?? --threshold=??
3. Make this request:
POST http://localhost:8080/report
headers: content-type: application/json
body: 
{
  "url": "http://www.sample.com"
}

# Design Issues:

1. Fixed Window Algorithm (as required)

Solution: Rolling Window Algorithm

2. A URL can be url-encoded or not (or even url-encoded multiple times).
 This service will identify each additional url-encoding as different url's