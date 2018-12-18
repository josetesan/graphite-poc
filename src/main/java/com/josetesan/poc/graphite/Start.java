package com.josetesan.poc.graphite;

import io.micrometer.core.instrument.Clock;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Metrics;
import io.micrometer.core.instrument.util.HierarchicalNameMapper;
import io.micrometer.graphite.GraphiteConfig;
import io.micrometer.graphite.GraphiteMeterRegistry;

public class Start
{

    public static void main(String[] args) {
            Metrics.addRegistry(graphiteRegistry());
            Looper looper = new Looper();
            looper.loop();
    }

    private static MeterRegistry graphiteRegistry() {
        GraphiteConfig graphiteConfig = new GraphiteConfig() {
            @Override
            public String host() {
                return "192.168.1.36";
            }

            @Override
            public String get(String k) {
                return null; // accept the rest of the defaults
            }
        };

        return new GraphiteMeterRegistry(graphiteConfig, Clock.SYSTEM,HierarchicalNameMapper.DEFAULT);
    }
}
