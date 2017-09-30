package tools.time;

import java.math.BigDecimal;

import static java.math.BigDecimal.ROUND_HALF_UP;

public class TimeHelper {
    private long startTime;
    private long endTime;

    public TimeHelper() {
        startTime = System.currentTimeMillis();
    }

    public long getSeconds() {
        endTime = System.currentTimeMillis();
        return ((endTime - startTime) / 1000);
    }

    public double getAccurateSeconds() {
        endTime = System.currentTimeMillis();
        BigDecimal p = new BigDecimal(endTime - startTime);
        BigDecimal e = new BigDecimal(1000);
        return (p.divide(e, 4, ROUND_HALF_UP)).doubleValue();
    }
}
