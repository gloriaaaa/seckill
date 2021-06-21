package com.gloria.miaosha.util;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang3.StringUtils;

//先去找initial初始化方法，初始化的时候拿到IsMobile注解，然后查看是否为必须的，如果是必须的IsValid会进行验证逻辑
public class IsMobileValidator implements ConstraintValidator<IsMobile,String>{

	private boolean required=false;
	public void initialize(IsMobile constraintAnnotation) {
		constraintAnnotation.required();
	}

	public boolean isValid(String value, ConstraintValidatorContext context) {
		if(required) {//查看值是否是必须的
			return ValidatorUtil.isMobile(value);
		}else {
			if(StringUtils.isEmpty(value)) {//required
				return true;
			}else {
				return ValidatorUtil.isMobile(value);
			}
		}
	}

}
