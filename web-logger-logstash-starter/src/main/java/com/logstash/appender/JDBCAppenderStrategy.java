package com.logstash.appender;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Conditional;

import com.logstash.conditions.DBCondition;
import com.logstash.domain.OperatorLog;
import com.logstash.enums.LogSaveType;
import com.logstash.jdbc.JDBCOpreator;

@Conditional(DBCondition.class)
public class JDBCAppenderStrategy implements LogAppenderStrategy {

	@Autowired
	private JDBCOpreator JDBCOpreator;

	@Override
	public void append(OperatorLog operatorLog) throws RuntimeException{
		try {
			if (JDBCOpreator == null) {
				throw new RuntimeException("请检查你的配置");
			}
			JDBCOpreator.save(operatorLog);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public LogSaveType support() {
		return LogSaveType.JDBC;
	}

}
