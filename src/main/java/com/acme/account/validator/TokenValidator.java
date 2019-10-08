package com.acme.account.validator;

import com.acme.account.validator.impl.TokenValidatorImpl;
import com.acme.account.validator.impl.UserValidatorImpl;

import javax.validation.Constraint;
import javax.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ PARAMETER })
@Retention(RUNTIME)
@Constraint(validatedBy = TokenValidatorImpl.class)
@Documented
public @interface TokenValidator {
  String message() default "Token is invalid!";

  Class<?>[] groups() default { };

  Class<? extends Payload>[] payload() default { };
}
