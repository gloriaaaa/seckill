package com.gloria.miaosha.exception;

import com.gloria.miaosha.result.CodeMsg;
import com.gloria.miaosha.result.Result;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@ControllerAdvice//是一个@Component，用于定义@ExceptionHandler、@InitBinder和ModelAttribute方法
//会对所有使用@RequestMapping方法进行检查拦截，并进行异常处理
@ResponseBody
//定义一个全局异常处理的拦截器
public class GlobalExceptionHandler {
	//拦截什么异常
	@ExceptionHandler(value=Exception.class)//标记拦截的异常，拦截所有的异常
	public Result<String> exceptionHandler(HttpServletRequest request, Exception e){
		e.printStackTrace();
		if(e instanceof GlobalException) {
			GlobalException ex=(GlobalException) e;
			CodeMsg cm=ex.getCm();
			return Result.error(cm);
		}
		if(e instanceof BindException) {//是绑定异常的情况
			//强转
			BindException ex=(BindException) e;
			//获取错误信息
			List<ObjectError> errors=ex.getAllErrors();
			ObjectError error=errors.get(0);
			String msg=error.getDefaultMessage();
			return Result.error(CodeMsg.BIND_ERROR.fillArgs(msg));
		}else {//不是绑定异常的情况
			return Result.error(CodeMsg.SERVER_ERROR);
		}
	}
}
