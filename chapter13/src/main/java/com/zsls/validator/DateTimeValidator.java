package com.zsls.validator;

import com.zsls.annotation.DateTime;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 *@ClassName DateTimeValidator
 *@Description 时间格式验证
 *
 *@Author zsls
 *@Date 2019/2/22 15:13
 *@Version 1.0
 */
public class DateTimeValidator implements ConstraintValidator<DateTime, String> {

	private DateTime dateTime;

	@Override
	public void initialize(DateTime constraintAnnotation) {
		this.dateTime = constraintAnnotation;
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
		// 如果 value 为空则不进行格式验证，为空验证可以使用 @NotBlank @NotNull @NotEmpty 等注解来进行控制，职责分离
		if (null == value) {
			return true;
		}
		String format = dateTime.format();

		if (value.length() != format.length()) {
			return false;
		}

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
		try {
			simpleDateFormat.parse(value);
		} catch (ParseException e) {
			return false;
		}

		return true;
	}
}
