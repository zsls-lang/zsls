package com.zsls.health;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

/**
 *@ClassName MyHealthIndicator
 *@Description 自定义健康端点
 *@Author zsls
 *@Date 2019/1/28 12:52
 *@Version 1.0
 * http://localhost:9001/mymonitor/health
 */
@Component("my1")
public class MyHealthIndicator implements HealthIndicator {
	private static final String VERSION = "v1.0.0";

	@Override
	public Health health() {
		int code = check();
		if (code != 0) {
			return Health.down().withDetail("code", code).withDetail("version", VERSION).withDetail("error","服务故障").build();
		}
		return Health.up().withDetail("code", code)
				.withDetail("version", VERSION).up().build();
	}

	private int check() {
		return 0;
	}
}
