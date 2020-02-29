# rate-limiter
Simple rate limiter service in Java + Springboot


Design Issues:

1. Fixed Window Algorithm (as required)
Solution: Rolling Window Algorithm

2. same url can be url-encoded or not (or even url-encoded multiple times).
 my service will identify each additional url-encoding as different url's