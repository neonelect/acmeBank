package com.acme.account.rest;

import com.acme.account.Util;
import com.acme.account.dto.TransactionType;
import com.acme.account.dto.response.TransactionDto;
import com.acme.account.dto.response.UserTransactionHistoryDto;
import com.acme.account.service.AccountService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(AccountHistoryController.class)
public class AccountHistoryControllerTest {

  private static final String ENDPOINT = "/history";

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private AccountService accountService;

  @Test
  public void getUserTransactionHistory() throws Exception {
    List<TransactionDto> mockList = getMockHistory();
    when(accountService.getUserTransactionHistory(anyString())).thenReturn(mockList);
    when(accountService.userExists(anyString())).thenReturn(true);

    MvcResult result = mockMvc.perform(MockMvcRequestBuilders
      .get(ENDPOINT + "/user/user1")
    ).andReturn();

    String responseJSON = result.getResponse().getContentAsString();
    assertNotNull(responseJSON);

    List<TransactionDto> resultList = Util.getStringAsObject(responseJSON, UserTransactionHistoryDto.class).getTransactions();
    assertEquals(3, resultList.size());
    mockList.retainAll(resultList);
    assertEquals(0, mockList.size());
  }

  @Test
  public void getUserTransactionHistoryBadUser() throws Exception {
    when(accountService.userExists(anyString())).thenReturn(false);
    ResultActions result = mockMvc.perform(MockMvcRequestBuilders
      .get(ENDPOINT + "/user/userX")
    )
      .andExpect(status().is4xxClientError());
  }


  private List<TransactionDto> getMockHistory() {
    List<TransactionDto> historyList = new ArrayList<>();

    historyList.add(TransactionDto.builder().type(TransactionType.INCREASE).value(100f).build());
    historyList.add(TransactionDto.builder().type(TransactionType.DECREASE).value(10f).build());
    historyList.add(TransactionDto.builder().type(TransactionType.INCREASE).value(1000f).build());

    return historyList;
  }
}