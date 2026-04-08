package com.mgr.api.validation.impl;

import com.mgr.api.validation.impl.GenderValidator;
import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = GenderValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface Gender {
    String message() default "Gender must be Male, Female or Other";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}