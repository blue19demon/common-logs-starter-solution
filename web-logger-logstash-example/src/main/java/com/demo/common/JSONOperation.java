package com.demo.common;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class JSONOperation<T> {

	private int code;
	private String message;
	private T data;
	
	public static JSONOperation<?> fail(String message) {
		return JSONOperation.builder().message(message).code(500).build();
	}
	
	public static JSONOperation<?> success(String message,Object data) {
		return  JSONOperation.builder().message(message).code(200).data(data).build();
	}

	public static JSONOperation<?> success(Object data) {
		return  JSONOperation.builder().message("操作成功").code(200).data(data).build();
	}
}
