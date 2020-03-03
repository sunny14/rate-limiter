package sunny14.ratelimiter.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sunny14.ratelimiter.service.RateLimiter;

import java.util.*;
import java.util.stream.Collectors;

public class RateLimiterImpl implements RateLimiter {

    private static Logger log = LoggerFactory.getLogger("RateLimiterImpl");

    private Map<Long, List<Date>> requests = new HashMap<>();

    private Long ttl, threshold;



    public RateLimiterImpl(Long ttl, Long threshold)    {
        this.ttl = ttl;
        this.threshold = threshold;
        log.info("creating rate limiter with ttl="+ttl+", threshold="+threshold);
    }

    @Override
    public boolean isBlocked(long id, Long incomeTs){

        List<Date> dates = requests.get(id);
        if (dates != null && !dates.isEmpty()) {
            dates = update(dates, incomeTs);
        }
        else {
            dates = new ArrayList<>();
        }

        dates.add(new Date(incomeTs));
        requests.put(id, dates);

        if (dates.size() <= this.threshold )   {
            return false;
        }

        return true;

    }

    private List<Date> update(List<Date> dates, Long ts) {
        Date newStart = new Date(ts-this.ttl);
        return dates.stream()
                .filter(date -> !date.before(newStart))
                .collect(Collectors.toList());

    }

}
