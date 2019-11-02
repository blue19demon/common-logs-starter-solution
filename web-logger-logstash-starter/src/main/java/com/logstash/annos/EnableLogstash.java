package com.logstash.annos;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;

import com.logstash.appender.FileAppenderStrategy;
import com.logstash.appender.JDBCAppenderStrategy;
import com.logstash.appender.LogAppenderContext;
import com.logstash.aspect.WebRequestLogAspect;
import com.logstash.config.LogstashProperties;
import com.logstash.service.OptLogService;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Import({
	LogstashProperties.class,
	WebRequestLogAspect.class,
	FileAppenderStrategy.class,
	JDBCAppenderStrategy.class,
	LogAppenderContext.class,
	OptLogService.class})
@EnableAspectJAutoProxy
public @interface EnableLogstash {

}
