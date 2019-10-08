package com.acme.account.validator.impl;

import com.acme.account.service.AccountService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserValidatorTest {

  @Autowired
  private UserValidatorImpl validator;

  @MockBean
  private AccountService accountService;

  @Test
  public void isValid() throws Exception {
    when(accountService.userExists(anyString())).thenReturn(true);

    assertTrue(validator.isValid("test", null));
  }

  @Test
  public void isNotValid() throws Exception {
    when(accountService.userExists(anyString())).thenReturn(false);

    assertFalse(validator.isValid("test", null));
  }

}