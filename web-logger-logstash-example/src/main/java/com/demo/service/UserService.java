package com.demo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSONObject;
import com.demo.domain.UserAuth;

@Service
//@LogstashModule("用户管理11")//本注解请使用在Controller上
public class UserService {

	private static List<UserAuth> list=new ArrayList<UserAuth>();
	static {
		list.add(UserAuth.builder().username("zhangsan").passwd("123").build());
		list.add(UserAuth.builder().username("李四").passwd("111").build());
	}
	//@API("用户登陆22")//本注解请使用在Controller上
	public JSONObject userLogin(UserAuth userAuth,Boolean isForce) {
		JSONObject jsonObject=new JSONObject();
		if(StringUtils.isEmpty(userAuth.getUsername())||
				StringUtils.isEmpty(userAuth.getPasswd())){
			jsonObject.put("msg", "用户名，密码不能为空");
			jsonObject.put("key", "faild");
			return jsonObject;
		}
		List<UserAuth> res = list.stream().filter(new Predicate<UserAuth>() {
			@Override
			public boolean test(UserAuth o) {
				return o.getUsername().equals(userAuth.getUsername())
						&&
						o.getPasswd().equals(userAuth.getPasswd());
			}
		}).collect(Collectors.toList());
		if(isForce) {
			if(res==null||res.size()==0) {
				jsonObject.put("msg", "用户名，密码错误");
				jsonObject.put("key", "faild");
				return jsonObject;
			}
		}
		jsonObject.put("key", "success");
		return jsonObject;
	}

	public List<UserAuth> list(String name) {
		return list.stream().filter(new Predicate<UserAuth>() {
			@Override
			public boolean test(UserAuth o) {
				if(StringUtils.isEmpty(name)) {
					return true;
				}
				return o.getUsername().equals(name);
			}
		}).collect(Collectors.toList());
	}

}
