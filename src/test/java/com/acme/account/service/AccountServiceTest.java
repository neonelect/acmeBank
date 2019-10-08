package com.acme.account.service;

import com.acme.account.dto.TransactionType;
import com.acme.account.dto.request.DecreaseRequestDto;
import com.acme.account.dto.response.TransactionDto;
import com.acme.account.exception.BadTokenException;
import com.acme.account.exception.DecreaseAmountException;
import com.acme.account.exception.UserWithoutHistoryException;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AccountServiceTest {

  @MockBean
  private TokenService tokenService;

  @Autowired
  private AccountService accountService;

  @Before
  public void init() {
    try {
      accountService.getUserTransactionHistory("user1").clear();
    } catch (UserWithoutHistoryException e){}
  }

  @Test
  public void getUserBalance() throws Exception {
   Float balance = accountService.getUserBalance("user1");
   assertNotNull(balance);
   assertTrue(balance >= 0);
  }

  @Test
  public void increaseUserBalance() throws Exception {
    Float previousBalance = accountService.getUserBalance("user1");
    accountService.increaseUserBalance("user1", 100f);
    Float balance = accountService.getUserBalance("user1");
    assertNotNull(balance);
    assertTrue(balance.equals(previousBalance + 100f));
  }

  @Test
  public void decreaseUserBalance() throws Exception {
    when(tokenService.isTokenValid(anyString())).thenReturn(true);
    when(tokenService.isUserAllowed(anyString(), anyString())).thenReturn(true);

    Float previousBalance = accountService.getUserBalance("user1");

    accountService.decreaseUserBalance("user1", DecreaseRequestDto.builder().value(100f).token("TEST").build());
    Float balance = accountService.getUserBalance("user1");
    assertNotNull(balance);
    assertTrue(balance.equals(previousBalance - 100f));
  }

  @Test
  public void getUserTransactionHistory() throws Exception {
    accountService.increaseUserBalance("user1", 100f);

    List<TransactionDto> list = accountService.getUserTransactionHistory("user1");

    assertTrue(list.size() == 1);

    TransactionDto dto = list.get(0);
    assertTrue(dto.getValue() == 100f);
    assertTrue(dto.getType() == TransactionType.INCREASE);
  }

  @Test
  public void userExists() throws Exception {
    assertTrue(accountService.userExists("user1"));
  }

  @Test(expected = UserWithoutHistoryException.class)
  public void getUserTransactionHistoryWithoutHistory(){
    accountService.getUserTransactionHistory("test");
  }

  @Test(expected = DecreaseAmountException.class)
  public void decreaseUserBalanceWithExceedingAmount(){
    when(tokenService.isTokenValid(anyString())).thenReturn(true);
    when(tokenService.isUserAllowed(anyString(), anyString())).thenReturn(true);

    accountService.decreaseUserBalance("user1", DecreaseRequestDto.builder().value(10000f).token("TEST").build());
  }

  @Test(expected = BadTokenException.class)
  public void decreaseUserBalanceWithBadToken(){
    accountService.decreaseUserBalance("user1", DecreaseRequestDto.builder().value(10000f).token("TEST").build());
  }

}