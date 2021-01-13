package com.example.demo.pojo;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class GenderValidator implements ConstraintValidator<Gender, String> {
    // 初始化校验值
    @Override
    public void initialize(Gender constraintAnnotation) {

    }

    // 校验规则
    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        return "男".equals(value) || "女".equals(value);
    }
}
