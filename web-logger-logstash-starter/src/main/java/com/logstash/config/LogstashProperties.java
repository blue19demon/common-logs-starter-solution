package com.logstash.config;

import javax.annotation.PostConstruct;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.util.StringUtils;

import com.logstash.enums.LogSaveType;

import lombok.Data;

@ConfigurationProperties(prefix = "logstash")
@Data
public class LogstashProperties {
    // 记录方式
    private String logType;
    // 如果是文件，配置文件路径
    private String logPath;
    // 系统编码
    private String sysCode;
    @PostConstruct
    public void init()  {
		if(sysCode==null){
			throw new IllegalArgumentException("請配置logstash.sysCode");
		}
		if(!StringUtils.isEmpty(logType)){
			if(!(logType.equals(LogSaveType.FILE.toString()))
					&&(!logType.equals(LogSaveType.JDBC.toString()))) {
				throw new IllegalArgumentException("請检查配置项logstash.logType,目前支持FILE和JDBC");
			}
			if(logType.equals(LogSaveType.FILE.toString())) {
				if(StringUtils.isEmpty(logPath)){
					throw new IllegalArgumentException("請配置logstash.logPath");
				}
			}
		}else {
			throw new IllegalArgumentException("請配置logstash.logType,目前支持FILE和JDBC");
		}
	}
}  