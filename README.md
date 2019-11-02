# common-logs-starter-solution
## 一个基于AOP的通用日志解决方案starter

#### 1.下载starter
#### 2.再使用方pom.xml加入如下配置
      <dependency>
	    <groupId>com.logstash</groupId>
	    <artifactId>web-logger-logstash-starter</artifactId>
	    <version>0.0.1-SNAPSHOT</version>
     </dependency>	
#### 3.启动类上加上@EnableLogstash注解开启starter
		@SpringBootApplication
		@EnableLogstash//开启starter
		public class WebLoggerLogstashDemoApplication {
			public static void main(String[] args) {
				SpringApplication springApplication = new SpringApplication(WebLoggerLogstashDemoApplication.class);
				springApplication.run(args);
			}
		}

#### 4.配置
	logstash:
			  这里支持FILE和JDBC两种方式
			  logType: JDBC
			  系统编码，必填
			  sysCode: example-system
			  logType为FILE时必填，生成的日志地址
			  logPath: web-logger-logstash-example.log
#### 5.附加配置
 #### 5.1 如果是logType为JDBC,请下载文件https://github.com/blue19demon/common-logs-starter-solution/blob/master/web-logger-logstash-starter/db/init.sql
 #### 5.2 到你的数据库中执行，并加入配置：
	 spring:
	  datasource:
	    driver-class-name: com.mysql.jdbc.Driver
	    url: jdbc:mysql://127.0.0.1:3308/test?serverTimezone=Asia/Shanghai&useUnicode=true&characterEncoding=utf-8&useSSL=false
	    username: root
	    password: 123456
 #### 5.3 加入配置
 
	/**
	 * logType JDBC時候配置
	 */
	@Configuration
	public class LogOpreatorConfig {
		@Autowired
		private JdbcTemplate jdbcTemplate;
		@Bean
		public JDBCOpreator jdbcOpreator() {
			return new JDBCOpreator() {
				@Override
				public void save(OperatorLog operatorLog) {
					jdbcTemplate.update(
							"INSERT INTO `operator_log`("
							+ "`excute_time`, "
							+ "`remote_addr`,"
							+ "`request_method`,"
							+ "`system`, "
							+ "`module`,"
							+ "`api`, "
							+ "`uri`, "
							+ "`params`, "
							+ "`session_id`, "
							+ "`cur_user`, "
							+ "`bean_name`, "
							+ "`result`,"
							+ "`time`, "
							+ "`exc_name`, "
							+ "`exc_message`, "
							+ "`created`) VALUES ("
							+ "?, "
							+ "?, "
							+ "?, "
							+ "?, "
							+ "?, "
							+ "?, "
							+ "?, "
							+ "?, "
							+ "?, "
							+ "?, "
							+ "?, "
							+ "?, "
							+ "?, "
							+ "?, "
							+ "?, "
							+ "?);",
							operatorLog.getExcuteTime(),
							operatorLog.getRemoteAddr(),
							operatorLog.getRequestMethod(),
							operatorLog.getSystem(),
							operatorLog.getModule(),
							operatorLog.getApi(),
							operatorLog.getUri(),
							operatorLog.getParams(),
							operatorLog.getSessionId(),
							operatorLog.getCurUser(),
							operatorLog.getBeanName(),
							operatorLog.getResult(),
							operatorLog.getTime(),
							operatorLog.getExcName(),
							operatorLog.getExcMessage(),
							new Date());
				}
			};
		}
	}
 #### 6.使用说明，请在Controller层使用，其它层的，比如service层，会导致记录的日志不准确，待解决！
	package com.demo.controller;
	@RestController
	@LogstashModule("用户管理")//加入该注解就会记录日志
	public class UserController {

		@Autowired
		private UserService userService;

		/**
		 * 登陆消息验证
		 */
		@RequestMapping(value = "/toLogin")
		@API("用户登陆")//接口功能介绍
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

#### 7.经过测试，不加注解的Controller就不会开启日志，对业务没有侵入性


#### 8.最终日志记录格式如下：
	2019-11-02 17:11:53.599  INFO 2584 --- [p-nio-82-exec-1] com.logstash.aspect.WebRequestLogAspect  : ===============请求内容===============
	2019-11-02 17:11:53.599  INFO 2584 --- [p-nio-82-exec-1] com.logstash.aspect.WebRequestLogAspect  : 请求地址:http://127.0.0.1:82/toLogin
	2019-11-02 17:11:53.599  INFO 2584 --- [p-nio-82-exec-1] com.logstash.aspect.WebRequestLogAspect  : 请求方式:POST
	2019-11-02 17:11:53.602  INFO 2584 --- [p-nio-82-exec-1] com.logstash.aspect.WebRequestLogAspect  : 请求参数:{"passwd":"123","username":"zhangsan"}
	2019-11-02 17:11:53.602  INFO 2584 --- [p-nio-82-exec-1] com.logstash.aspect.WebRequestLogAspect  : 响应结果 : JSONOperation(code=200, message=登陆成功!, data=UserAuth(username=zhangsan, passwd=123))
	2019-11-02 17:11:53.606  INFO 2584 --- [p-nio-82-exec-1] com.logstash.aspect.WebRequestLogAspect  : 请求耗时：1252ms
	2019-11-02 17:11:53.606  INFO 2584 --- [p-nio-82-exec-1] com.logstash.aspect.WebRequestLogAspect  : ===============请求内容===============
	2019-11-02 17:11:53.625  INFO 2584 --- [p-nio-82-exec-1] com.logstash.aspect.WebRequestLogAspect  : {
		"api":"用户登陆",
		"beanName":"com.demo.controller.UserController",
		"excuteTime":1252,
		"module":"用户管理",
		"params":"{\"passwd\":\"123\",\"username\":\"zhangsan\"}",
		"remoteAddr":"127.0.0.1",
		"requestMethod":"POST",
		"result":"JSONOperation(code=200, message=登陆成功!, data=UserAuth(username=zhangsan, passwd=123))",
		"sessionId":"80585F127FB81113F56CB3B08F89EBAA",
		"system":"example-system",
		"time":"2019年11月02日 17时11分52.712秒",
		"uri":"http://127.0.0.1:82/toLogin"
	}

####  9.异常情况下也会记录到日志

