package sunny14.ratelimiter.service;

import sunny14.ratelimiter.service.exceptions.HasherException;

public interface Hasher {

    String hash(String str) throws HasherException;
}
