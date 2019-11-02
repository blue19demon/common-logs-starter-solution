package com.config;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import com.logstash.domain.OperatorLog;
import com.logstash.jdbc.JDBCOpreator;

/**
 * logType JDBC時候配置
 */
@Configuration
public class LogOpreatorConfig {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Bean
	public JDBCOpreator jdbcOpreator() {
		return new JDBCOpreator() {
			@Override
			public void save(OperatorLog operatorLog) {
				jdbcTemplate.update(
						"INSERT INTO `operator_log`("
						+ "`excute_time`, "
						+ "`remote_addr`,"
						+ "`request_method`,"
						+ "`system`, "
						+ "`module`,"
						+ "`api`, "
						+ "`uri`, "
						+ "`params`, "
						+ "`session_id`, "
						+ "`cur_user`, "
						+ "`bean_name`, "
						+ "`result`,"
						+ "`time`, "
						+ "`exc_name`, "
						+ "`exc_message`, "
						+ "`created`) VALUES ("
						+ "?, "
						+ "?, "
						+ "?, "
						+ "?, "
						+ "?, "
						+ "?, "
						+ "?, "
						+ "?, "
						+ "?, "
						+ "?, "
						+ "?, "
						+ "?, "
						+ "?, "
						+ "?, "
						+ "?, "
						+ "?);",
						operatorLog.getExcuteTime(),
						operatorLog.getRemoteAddr(),
						operatorLog.getRequestMethod(),
						operatorLog.getSystem(),
						operatorLog.getModule(),
						operatorLog.getApi(),
						operatorLog.getUri(),
						operatorLog.getParams(),
						operatorLog.getSessionId(),
						operatorLog.getCurUser(),
						operatorLog.getBeanName(),
						operatorLog.getResult(),
						operatorLog.getTime(),
						operatorLog.getExcName(),
						operatorLog.getExcMessage(),
						new Date());
			}
		};
	}
}
