package com.acme.account.service;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.constraints.AssertTrue;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TokenServiceTest {

  @Autowired
  private TokenService tokenService;

  @Test
  public void removeKey() throws Exception {
    String token = tokenService.getToken("test");
    assertTrue(tokenService.isTokenValid(token));

    tokenService.removeKey("test");
    assertFalse(tokenService.isTokenValid(token));
  }

  @Test
  public void getToken() throws Exception {
    String token = tokenService.getToken("test");
    assertNotNull(token);
    assertTrue(StringUtils.isNotEmpty(token));
  }

  @Test
  public void isUserAllowed() throws Exception {
    String token = tokenService.getToken("test");

    assertTrue(tokenService.isUserAllowed("test", token));
  }

  @Test
  public void isTokenValid() throws Exception {
    String token = tokenService.getToken("test");

    assertTrue(tokenService.isTokenValid(token));
  }

}