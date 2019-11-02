package com.logstash.conditions;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.util.StringUtils;

import com.logstash.enums.LogSaveType;

public class DBCondition implements Condition {

    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
    	String logType  = context.getEnvironment().getProperty("logstash.logType");
    	return !StringUtils.isEmpty(logType)&&logType.equals(LogSaveType.JDBC.toString());
    }
}