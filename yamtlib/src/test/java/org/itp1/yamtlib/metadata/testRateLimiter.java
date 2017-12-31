package org.itp1.yamtlib.metadata;

import com.google.common.util.concurrent.RateLimiter;
import org.junit.Assert;
import org.junit.Test;

public class testRateLimiter {

    @Test
    // 3 requests per second are allowed to accoustid so if 7 are made it needs to take more than 2 secs and if you make 3 it needs to take more than 1 sec
    public void testRateLimiter() {
        RateLimiter rateLimiter = RateLimiter.create(2.98); // rate is "2.98 permits per second"

        long startTime = System.nanoTime();
        for(int i = 0; i < 7; i++) {
            rateLimiter.acquire(1);
            // do something
            System.out.println("test" + (i + 1));
        }
        long elapsedTimeNanoSeconds = System.nanoTime() - startTime;
        double elapsedTimeSeconds = (double)elapsedTimeNanoSeconds/1000000000;
        Assert.assertTrue(elapsedTimeSeconds > 2);

        startTime = System.nanoTime();
        for(int i = 0; i < 4; i++) {
            rateLimiter.acquire(1);
            // do something
            System.out.println("test" + (i + 1));
        }
        elapsedTimeNanoSeconds = System.nanoTime() - startTime;
        elapsedTimeSeconds = (double)elapsedTimeNanoSeconds/1000000000;
        Assert.assertTrue(elapsedTimeSeconds > 1);
    }

}
