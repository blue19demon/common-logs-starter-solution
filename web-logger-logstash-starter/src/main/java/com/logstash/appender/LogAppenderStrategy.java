package com.logstash.appender;

import com.logstash.domain.OperatorLog;
import com.logstash.enums.LogSaveType;

public interface LogAppenderStrategy {

	public void append(OperatorLog operatorLog);
	
	public LogSaveType support();
}
