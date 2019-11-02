package com.logstash.appender;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.logstash.domain.OperatorLog;
import com.logstash.enums.LogSaveType;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LogAppenderContext{

	@Autowired
	private List<LogAppenderStrategy> logAppenderStrategys;

	public String append(OperatorLog operatorLog, LogSaveType logSaveType) {
		try {
			LogAppenderStrategy logAppenderStrategy = chooseOne(logSaveType);
			logAppenderStrategy.append(operatorLog);
			return "SAVE SUCCESS";
		} catch (Exception e) {
			e.printStackTrace();
			return "SAVE FAILED";
		}
	}

	private LogAppenderStrategy chooseOne(LogSaveType logSaveType) {
		log.info("接口："+logAppenderStrategys);
		for (LogAppenderStrategy logAppenderStrategy : logAppenderStrategys) {
			if(logSaveType == logAppenderStrategy.support()) {
				return logAppenderStrategy;
			}
		}
		throw new IllegalArgumentException("非法参数logSaveType");
	}
	
}
