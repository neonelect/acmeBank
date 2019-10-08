package com.acme.account.rest;

import com.acme.account.dto.response.TokenDto;
import com.acme.account.service.TokenService;
import com.acme.account.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tokens/user")
@Validated
public class TokenController extends AbstractRestController {

  @Autowired
  private TokenService tokenService;

  @PostMapping(value = "/{userId}", produces = "application/json")
  public ResponseEntity<TokenDto> getToken(@PathVariable @UserValidator String userId){
    return createResponse(
      TokenDto.builder().token(tokenService.getToken(userId)).build(),
      HttpStatus.CREATED
    );
  }

}
