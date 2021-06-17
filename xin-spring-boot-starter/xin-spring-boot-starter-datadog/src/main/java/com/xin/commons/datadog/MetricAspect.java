package com.xin.commons.datadog;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.annotation.Priority;

@Slf4j
@Aspect
@Priority(10000)
@Component
public class MetricAspect {

	@Autowired
	private TimeMetric timeMetric;

	@Around("@annotation(metricWith)")
	public Object metricAspect(ProceedingJoinPoint joinPoint, MetricWith metricWith) throws Throwable {
		try {
			timeMetric.time(metricWith.value());
		}catch (Exception e){
			log.error("MetricAspect失败，失败原因：{}",e);
		}
		return joinPoint.proceed();
	}
}
