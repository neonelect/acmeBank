package com.acme.account.rest;

import com.acme.account.service.AccountService;
import com.acme.account.service.TokenService;
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

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(AccountController.class)
public class AccountControllerTest {

  private static final String ENDPOINT = "/balance/user";

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private AccountService accountService;

  @MockBean
  private TokenService tokenService;

  @Test
  public void getUserBalance() throws Exception {
    when(accountService.userExists(anyString())).thenReturn(true);
    when(accountService.getUserBalance(anyString())).thenReturn(1000f);
    ResultActions result = mockMvc.perform(MockMvcRequestBuilders
      .get(ENDPOINT + "/user1")
    )
      .andExpect(status().isOk());

    assertEquals("{\"value\":1000.0}", result.andReturn().getResponse().getContentAsString());
  }

  @Test
  public void increaseUserBalance() throws Exception {
    when(accountService.userExists(anyString())).thenReturn(true);
    ResultActions result = mockMvc.perform(MockMvcRequestBuilders
      .post(ENDPOINT + "/user1/increase")
      .contentType(MediaType.APPLICATION_JSON)
      .content("{\"value\":100.0}")
    )
      .andExpect(status().isOk());
  }

  @Test
  public void decreaseUserBalance() throws Exception {
    when(accountService.userExists(anyString())).thenReturn(true);
    when(tokenService.isTokenValid(anyString())).thenReturn(true);
    ResultActions result = mockMvc.perform(MockMvcRequestBuilders
      .post(ENDPOINT + "/user1/decrease")
      .contentType(MediaType.APPLICATION_JSON)
      .content("{\"value\":100.0, \"token\":\"test\"}")
    )
      .andExpect(status().isOk());
  }

  @Test
  public void getUserBalanceBadUser() throws Exception {
    when(accountService.userExists(anyString())).thenReturn(false);
    MvcResult result = mockMvc.perform(MockMvcRequestBuilders
      .get(ENDPOINT + "/user1")
    ).andExpect(status().is4xxClientError())
      .andReturn();
  }

  @Test
  public void increaseUserBalancebadUser() throws Exception {
    when(accountService.userExists(anyString())).thenReturn(false);
    ResultActions result = mockMvc.perform(MockMvcRequestBuilders
      .post(ENDPOINT + "/userX/increase")
      .contentType(MediaType.APPLICATION_JSON)
      .content("{\"value\":100.0}")
    )
      .andExpect(status().is4xxClientError());
  }

  @Test
  public void decreaseUserBalanceBadUser() throws Exception {
    when(accountService.userExists(anyString())).thenReturn(false);
    when(tokenService.isTokenValid(anyString())).thenReturn(true);
    ResultActions result = mockMvc.perform(MockMvcRequestBuilders
      .post(ENDPOINT + "/userX/decrease")
      .contentType(MediaType.APPLICATION_JSON)
      .content("{\"value\":100.0, \"token\":\"test\"}")
    )
      .andExpect(status().is4xxClientError());
  }

  @Test
  public void decreaseUserBalanceBadRequest() throws Exception {
    when(accountService.userExists(anyString())).thenReturn(true);
    when(tokenService.isTokenValid(anyString())).thenReturn(true);
    ResultActions result = mockMvc.perform(MockMvcRequestBuilders
      .post(ENDPOINT + "/user1/decrease")
      .contentType(MediaType.APPLICATION_JSON)
      .content("{\"value\":100.0}")
    )
      .andExpect(status().is4xxClientError());
  }

  @Test
  public void decreaseUserBalanceBadMediaType() throws Exception {
    when(accountService.userExists(anyString())).thenReturn(true);
    when(tokenService.isTokenValid(anyString())).thenReturn(true);
    ResultActions result = mockMvc.perform(MockMvcRequestBuilders
      .post(ENDPOINT + "/user1/decrease")
      .content("{\"value\":100.0, \"token\":\"test\"}")
    )
      .andExpect(status().is4xxClientError());
  }

  @Test
  public void decreaseUserBalanceBadToken() throws Exception {
    when(accountService.userExists(anyString())).thenReturn(true);
    when(tokenService.isTokenValid(anyString())).thenReturn(false);
    ResultActions result = mockMvc.perform(MockMvcRequestBuilders
      .post(ENDPOINT + "/user1/decrease")
      .contentType(MediaType.APPLICATION_JSON)
      .content("{\"value\":100.0, \"token\":\"test\"}")
    )
      .andExpect(status().is4xxClientError());
  }

}