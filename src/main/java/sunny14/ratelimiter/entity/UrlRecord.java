package sunny14.ratelimiter.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RedisHash("UrlRecord")
public class UrlRecord {

    @Id
    private String hashedUrl;

    private int count = 0;
    private List<Date> tsList = new ArrayList();

    public UrlRecord(String hashedUrl, List <Date> tsList)   {
        this.hashedUrl = hashedUrl;
        this.count = 1;
        this.tsList = tsList;
    }


    private void update(Long ts, Long ttl) {
        Date newStart = new Date(ts-ttl);
        this.tsList = tsList.stream()
                .filter(date -> !date.before(newStart))
                .collect(Collectors.toList());
        this.count = this.tsList.size();
    }

    public boolean inc(Long ts, Long ttl, Long threshold) {

        this.update(ts, ttl);

        if (this.count >= threshold )   {
            return false;
        }

        this.tsList.add(new Date(ts));
        this.count++;

        return true;
    }
}
