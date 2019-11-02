package com.demo.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.demo.common.JSONOperation;
import com.demo.domain.UserAuth;
import com.demo.service.UserService;
import com.logstash.annos.API;
import com.logstash.annos.LogstashModule;

@RestController
@LogstashModule("用户管理")//日志模块
public class UserController {

	@Autowired
	private UserService userService;

	/**
	 * 登陆消息验证
	 */
	@RequestMapping(value = "/toLogin")
	@API("用户登陆")
	public JSONOperation<?> toLogin(UserAuth userAuth, HttpServletRequest request, HttpServletRequest response) {
		//System.out.println(1/0);//测试异常
		JSONObject loginJson = userService.userLogin(userAuth,true);
		// 表示登陆成功
		if (loginJson.get("key").equals("success")) {
			return JSONOperation.success("登陆成功!", userAuth);
		}
		return JSONOperation.fail(loginJson.getString("msg"));
	}
	
	/**
	 * 用户列表
	 */
	@RequestMapping(value = "/list")
	@API("用户列表")
	public JSONOperation<?> list(String name, HttpServletRequest request, HttpServletRequest response) {
		List<UserAuth> list = userService.list(name);
		return JSONOperation.success(list);
	}
}
