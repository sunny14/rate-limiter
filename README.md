# rate-limiter
Simple rate limiter service in Java + Springboot + Redis

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
