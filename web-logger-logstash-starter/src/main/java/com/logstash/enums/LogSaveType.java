package com.logstash.enums;

public enum LogSaveType {
	JDBC("数据库"),
	FILE("文件");
	private String desc;

	public String getDesc() {
		return desc;
	}
	private LogSaveType(String desc) {
		this.desc = desc;
	}
	
}
