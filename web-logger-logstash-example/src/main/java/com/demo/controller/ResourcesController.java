package com.demo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.common.JSONOperation;

@RestController
//不加这句，表示不收集日志
//@LogstashModule("资源管理")
public class ResourcesController {

	@RequestMapping(value = "/resources")
	//@API("资源记录")
	public JSONOperation<?> resources() {
		return JSONOperation.success("this is resources");
	}
	
}
