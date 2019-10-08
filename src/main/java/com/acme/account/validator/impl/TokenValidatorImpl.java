/**
 * This computer program is protected by copyright law and international treaties.
 * Unauthorized reproduction or distribution of this program, or any portion of it may result in severe civil and criminal penalties, and will be prosecuted to the maximum extent possible under the law.
 * <p>
 * Copyright © 2019 Lufthansa Systems Poland.
 * All rights reserved.
 */
package com.acme.account.validator.impl;

import com.acme.account.dto.request.DecreaseRequestDto;
import com.acme.account.service.TokenService;
import com.acme.account.validator.TokenValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Token validator
 */
@Component
public class TokenValidatorImpl implements ConstraintValidator<TokenValidator, DecreaseRequestDto> {

  @Autowired
  private TokenService tokenService;

  @Override
  public void initialize(TokenValidator constraintAnnotation) {
  }

  /**
   * Checks if the token is valid
   *
   * @param decreaseRequestDto value with token
   * @param constraintValidatorContext validation context
   * @return true if token exists
   */
  @Override
  public boolean isValid(DecreaseRequestDto decreaseRequestDto, ConstraintValidatorContext constraintValidatorContext) {
    return tokenService.isTokenValid(decreaseRequestDto.getToken());
  }
}
