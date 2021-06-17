package com.xin.commons.datadog;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import com.timgroup.statsd.NonBlockingStatsDClient;
import com.timgroup.statsd.StatsDClient;
import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Default StatsD provider: Datadog.
 */
@Component
public class DatadogService implements StatsD {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Value("${xin.setting.datadog.agent.prefix:xin}")
	private String datadogAgentPrefix;

	@Value("${xin.setting.datadog.agent.host:localhost}")
	private String datadogAgentHost;

	@Value("${xin.setting.datadog.agent.port:8125}")
	private int datadogAgentPort;

	@Value("${spring.application.name:unamed-app}")
	private String appName;

	@Value("${spring.profiles.active:local}")
	private String appProfiles;

	private StatsDClient client;

	@PostConstruct
	public void init() {
		List<String> profileList = Arrays.stream(this.appProfiles.split(",")).map(s -> s.trim()).filter(s -> !s.isEmpty()).map(s -> "profile:" + s).collect(Collectors.toList());
		List<String> tagList = new ArrayList<>(profileList);
		tagList.add("app:" + this.appName);
		String[] tags = tagList.toArray(new String[tagList.size()]);
		logger.info("初始化datadog tags = {}", String.join(", ", tags));
		this.client = new NonBlockingStatsDClient(datadogAgentPrefix, datadogAgentHost, datadogAgentPort, tags);
	}

	/**
	 * 统计指标
	 * @param name
	 * @param delta
	 * @param tags
	 */
	@Override
	public void count(String name, long delta, String... tags) {

		client.count(name, delta, tags);
	}

	/**
	 * 耗时监控指标
	 * @param aspect
	 * @param value
	 * @param tags
	 */
	@Override
	public void time(String aspect, long value, String... tags) {

		client.time(aspect, value, tags);
	}

	/**
	 * 普通打点指标
	 * @param aspect
	 * @param value
	 * @param tags
	 */
	@Override
	public void histogram(String aspect, long value, String... tags) {

		client.histogram(aspect, value, tags);
	}

	/**
	 * 变动数值监控指标
	 * @param aspect
	 * @param value
	 * @param tags
	 */
	@Override
	public void gauge(String aspect, double value, String... tags) {

		client.gauge(aspect, value, tags);
	}

}
