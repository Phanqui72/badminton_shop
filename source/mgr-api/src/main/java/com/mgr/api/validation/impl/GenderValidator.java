package com.mgr.api.validation.impl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.List;

import static com.mgr.api.constant.MgrConstant.*;

public class GenderValidator implements ConstraintValidator<Gender, Integer> {

    private final List<Integer> GENDERS = Arrays.asList(GENDER_TYPE_MALE, GENDER_TYPE_FEMALE, GENDER_TYPE_OTHER);

    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        return GENDERS.contains(value);
    }
}