package com.xin.commons.support.datadog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TimeMetric implements AutoCloseable{

    private static String prefix = "system.module.";

    @Autowired
    private DatadogService datadogService;

    private Metrics.Time time;
    private String[] tags;
    private long startTime;

    private TimeMetric(Metrics.Time time, String... tags) {
        this.time = time;
        this.tags = tags;
        this.startTime = System.currentTimeMillis();
    }

    @Override
    public void close() {
        time(this.time, System.currentTimeMillis() - this.startTime, this.tags);
    }

    /**
     * 性能监控，用法：
     * try (var timer = Metrics.time(Metrics.Time.PROCESS_EVENT)) {}
     */
    public TimeMetric time(Metrics.Time aspect, String... tags) {
        return new TimeMetric(aspect, tags);
    }

    /**
     * 性能监控，用法：
     * long start = System.currentMillis();
     * Metrics.time(Metrics.Time.PROCESS_EVENT, System.currentMillis() - start);
     */
    public void time(Metrics.Time aspect, long value, String... tags) {
        datadogService.time(prefix + aspect.value, value, tags);
    }
}
