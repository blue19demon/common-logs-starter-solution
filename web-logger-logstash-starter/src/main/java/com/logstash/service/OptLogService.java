package com.logstash.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.logstash.appender.LogAppenderContext;
import com.logstash.config.LogstashProperties;
import com.logstash.domain.OperatorLog;
import com.logstash.enums.LogSaveType;

public class OptLogService {

	@Autowired
	private LogstashProperties properties;
	@Autowired
	private LogAppenderContext logAppenderContext;
	public String saveLog(OperatorLog optLog) {
		return logAppenderContext.append(optLog, LogSaveType.valueOf(properties.getLogType()));
	}

}
