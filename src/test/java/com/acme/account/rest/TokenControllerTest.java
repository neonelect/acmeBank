package com.acme.account.rest;

import com.acme.account.Util;
import com.acme.account.dto.response.TokenDto;
import com.acme.account.service.AccountService;
import com.acme.account.service.TokenService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(TokenController.class)
public class TokenControllerTest {

  private static final String ENDPOINT = "/tokens/user";

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private TokenService tokenService;

  @MockBean
  private AccountService accountService;

  @Test
  public void getToken() throws Exception {
    when(tokenService.getToken(anyString())).thenReturn("TEST");
    when(accountService.userExists(anyString())).thenReturn(true);

    MvcResult result = mockMvc.perform(MockMvcRequestBuilders
      .post(ENDPOINT + "/user1")
    ).andReturn();

    String resultString = result.getResponse().getContentAsString();

    assertNotNull(resultString);
    assertEquals("TEST", Util.getStringAsObject(resultString, TokenDto.class).getToken());
  }

  @Test
  public void getTokenBadUser() throws Exception {
    when(accountService.userExists(anyString())).thenReturn(false);
    ResultActions result = mockMvc.perform(MockMvcRequestBuilders
      .get(ENDPOINT + "/userX")
    )
      .andExpect(status().is4xxClientError());
  }

}