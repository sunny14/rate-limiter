package sunny14.ratelimiter.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import sunny14.ratelimiter.entity.UrlRecord;

@Repository
public interface UrlRepo extends CrudRepository<UrlRecord, String> {}
