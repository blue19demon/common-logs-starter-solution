package com.logstash.jdbc;

import com.logstash.domain.OperatorLog;

public interface JDBCOpreator {

	void save(OperatorLog operatorLog);

}
