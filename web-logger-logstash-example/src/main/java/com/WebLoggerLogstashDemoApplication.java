package com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.logstash.annos.EnableLogstash;

@SpringBootApplication
@EnableLogstash//开启starter
public class WebLoggerLogstashDemoApplication {
	public static void main(String[] args) {
		SpringApplication springApplication = new SpringApplication(WebLoggerLogstashDemoApplication.class);
		springApplication.run(args);
	}
}
