package com.acme.account.validator.impl;

import com.acme.account.dto.request.DecreaseRequestDto;
import com.acme.account.service.AccountService;
import com.acme.account.service.TokenService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TokenValidatorTest {

  @Autowired
  private TokenValidatorImpl validator;

  @MockBean
  private TokenService tokenService;

  @Test
  public void isValid() throws Exception {
    when(tokenService.isTokenValid(anyString())).thenReturn(true);

    assertTrue(validator.isValid(DecreaseRequestDto.builder().token("TEST").value(1f).build(), null));
  }

  @Test
  public void isNotValid() throws Exception {
    when(tokenService.isTokenValid(anyString())).thenReturn(false);

    assertFalse(validator.isValid(DecreaseRequestDto.builder().token("TEST").value(1f).build(), null));
  }

}