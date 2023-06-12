package com.devizones.api.validation;

import com.devizones.commons.CodeEnum;
import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static java.lang.annotation.ElementType.*;

@Constraint(validatedBy = EnumValid.EnumValidValidator.class)
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER})
public @interface EnumValid {
    String message() default "";

    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default{};
    Class<? extends CodeEnum> target();

    class EnumValidValidator implements ConstraintValidator<EnumValid, Object> {
        private List<Object> validList;

        @Override
        public void initialize(EnumValid constraintAnnotation) {
            validList = Arrays
                    .stream(constraintAnnotation.target().getEnumConstants())
                    .map(CodeEnum::getName)
                    .collect(Collectors.toList());
        }

        @Override
        public boolean isValid(Object value, ConstraintValidatorContext context) {
            return validList.contains(value);
        }
    }
}