package com.gloria.miaosha.controller;

import ch.qos.logback.classic.Logger;
import com.gloria.miaosha.redis.RedisService;
import com.gloria.miaosha.result.CodeMsg;
import com.gloria.miaosha.result.Result;
import com.gloria.miaosha.service.MiaoshaUserService;
import com.gloria.miaosha.service.UserService;
import com.gloria.miaosha.vo.LoginVo;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
@RequestMapping("/login")
@Controller
public class LoginController {
	@Autowired
	UserService userService;
	@Autowired
	RedisService redisService;
	
	@Autowired
	MiaoshaUserService miaoshaUserService;
	
	//slf4j
	private static Logger log=(Logger) LoggerFactory.getLogger(Logger.class);
	
	@RequestMapping("/to_login")
	public String toLogin() {
		return "login";//返回页面login
	}
	
	//使用JSR303校验
	@RequestMapping("/do_login_test")//作为异步操作
	@ResponseBody
	public Result<String> doLogintest(HttpServletResponse response, @Valid LoginVo loginVo) {//0代表成功
		//log.info(loginVo.toString());
		//参数校验  使用了注解参数校验
//		String passInput=loginVo.getPassword();
//		String mobile=loginVo.getMobile();
//		if(StringUtils.isEmpty(passInput)) {
//			return Result.error(CodeMsg.PASSWORD_EMPTY);
//		}
//		if(StringUtils.isEmpty(mobile)) {
//			return Result.error(CodeMsg.MOBILE_EMPTY);
//		}
//		System.out.println("mobile："+mobile);
//		if(!ValidatorUtil.isMobile(mobile)) {//手机号验证不通过 false
//			return Result.error(CodeMsg.MOBILE_ERROR);
//		}
		//参数检验成功之后，登录
		//CodeMsg cm=miaoshaUserService.login(response,loginVo);
		String token=miaoshaUserService.loginString(response,loginVo);
//		if(cm.getCode()==0) {
//			return Result.success(true);
//		}else {
//			return Result.error(cm);
//		}
		return Result.success(token);
	}
	
	
	
	  //使用JSR303校验
		@RequestMapping("/do_login")//作为异步操作
		@ResponseBody
		/**
		 * 这里用实体类接收前端提交的json，Controller还可以通过HttpServletResponse等获取请求参数
		 * 几种方法总结见 https://www.cnblogs.com/lcxdevelop/p/8309018.html
		 */
		public Result<Boolean> doLogin(HttpServletResponse response,@Valid LoginVo loginVo) {//0代表成功
			//log.info(loginVo.toString());
			//参数校验  使用了注解参数校验
//			String passInput=loginVo.getPassword();
//			String mobile=loginVo.getMobile();
//			if(StringUtils.isEmpty(passInput)) {
//				return Result.error(CodeMsg.PASSWORD_EMPTY);
//			}
//			if(StringUtils.isEmpty(mobile)) {
//				return Result.error(CodeMsg.MOBILE_EMPTY);
//			}
//			System.out.println("mobile："+mobile);
//			if(!ValidatorUtil.isMobile(mobile)) {//手机号验证不通过 false
//				return Result.error(CodeMsg.MOBILE_ERROR);
//			}
			//参数检验成功之后，登录
			CodeMsg cm=miaoshaUserService.login(response,loginVo);
			if(cm.getCode()==0) {
				return Result.success(true);
			}else {
				return Result.error(cm);
			}
		}
}
