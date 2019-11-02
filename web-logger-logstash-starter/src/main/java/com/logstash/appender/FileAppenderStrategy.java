package com.logstash.appender;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Conditional;

import com.alibaba.fastjson.JSONObject;
import com.logstash.conditions.FileCondition;
import com.logstash.config.LogstashProperties;
import com.logstash.domain.OperatorLog;
import com.logstash.enums.LogSaveType;
@Conditional(FileCondition.class)
public class FileAppenderStrategy implements LogAppenderStrategy{
	@Autowired
	private LogstashProperties properties;
	@Override
	public void append(OperatorLog operatorLog) throws RuntimeException{
		FileWriter fileWriter = null;
		try {
			File logFile =new File(properties.getLogPath());
			fileWriter = new FileWriter(logFile,true);//创建文本文件
			fileWriter.write(JSONObject.toJSONString(operatorLog, true)+"\r\n");//写入 \r\n换行
			fileWriter.flush();
			fileWriter.close();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public LogSaveType support() {
		return LogSaveType.FILE;
	}

}
