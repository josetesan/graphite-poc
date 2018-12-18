package com.josetesan.poc.graphite;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.DistributionSummary;
import io.micrometer.core.instrument.Metrics;
import io.micrometer.core.instrument.Timer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Looper {

    private static final Logger LOGGER = LoggerFactory.getLogger(Looper.class);

    private Counter counter;
    private Timer timer;
    private DistributionSummary summary;
    private AtomicInteger sleep;

    public Looper(){
         counter = Metrics.counter("databus","test=counter","app=bus");
         timer = Metrics.timer("databus", "test=timer","app=bus");
         summary = Metrics.summary("databus", "test=summary","app=bus");
         sleep  = Metrics.gauge("databus", new AtomicInteger(0));
    }

    public void loop()  {
        while (true) {
            timer.record(() -> {
                try {
                    sleep.set(getRandomNumberInRange(250,1000));
                    summary.record(sleep.get());
                    Thread.sleep(sleep.get());
                    counter.increment();
                    LOGGER.info("Counter is {}, and sleep is {}",
                        counter.count(),
                        sleep.intValue());
                } catch (Exception e) {

                }
            });

        }
    }

    private static int getRandomNumberInRange(int min, int max) {

        Random r = new Random();
        return r.ints(min, (max + 1)).findFirst().getAsInt();

    }
}
