package com.xin.commons.datadog;

/**
 * StatsD interface.
 */
public interface StatsD {

	default void increase(String aspect, String... tags) {
		count(aspect, 1, tags);
	}

	void count(String aspect, long delta, String... tags);

	void time(String aspect, long value, String... tags);

	void histogram(String aspect, long value, String... tags);

	void gauge(String aspect, double value, String... tags);

}
