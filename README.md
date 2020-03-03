# rate-limiter
Simple rate limiter service in Java + Springboot + Redis

# How to run
1. Checkout from GitHub
2. Run class RateLimitApp with following OPTIONAL arguments:
--ttl=?? --threshold=??
3. Make this request:
CURL http://localhost:8080/?clientId=1
