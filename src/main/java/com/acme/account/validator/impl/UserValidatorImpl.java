/**
 * This computer program is protected by copyright law and international treaties.
 * Unauthorized reproduction or distribution of this program, or any portion of it may result in severe civil and criminal penalties, and will be prosecuted to the maximum extent possible under the law.
 * <p>
 * Copyright © 2019 Lufthansa Systems Poland.
 * All rights reserved.
 */
package com.acme.account.validator.impl;

import com.acme.account.service.AccountService;
import com.acme.account.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
public class UserValidatorImpl implements ConstraintValidator<UserValidator, String> {

  @Autowired
  private AccountService accountService;

  @Override
  public void initialize(UserValidator constraintAnnotation) {
  }

  @Override
  public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
    return accountService.userExists(s);
  }
}
