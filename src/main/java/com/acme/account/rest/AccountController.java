package com.acme.account.rest;

import com.acme.account.dto.request.DecreaseRequestDto;
import com.acme.account.dto.request.ValueDto;
import com.acme.account.exception.DecreaseAmountException;
import com.acme.account.service.AccountService;
import com.acme.account.validator.TokenValidator;
import com.acme.account.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Account endpoints class
 */
@RestController
@RequestMapping("/balance/user")
@Validated
public class AccountController extends AbstractRestController {

  @Autowired
  private AccountService accountService;

  /**
   * Returns user balance
   *
   * @param userId proper user ID
   * @return pure balance amount
   */
  @GetMapping(value = "/{userId}")
  public ResponseEntity getUserBalance(@PathVariable @UserValidator String userId){
    return createOkResponse(ValueDto.builder().value(accountService.getUserBalance(userId)).build());
  }

  /**
   * Increases users balance by the given amount
   *
   * @param userId proper user ID
   * @param valueDto increase amount
   */
  @PostMapping(value = "/{userId}/increase", consumes = "application/json")
  public void increaseUserBalance(@PathVariable @UserValidator String userId, @Valid @RequestBody ValueDto valueDto){
    accountService.increaseUserBalance(userId, valueDto.getValue());
  }

  /**
   * Decreases users balance by the given amount
   *
   * @param userId proper user ID
   * @param decreaseRequestDto value with token
   */
  @PostMapping(value = "/{userId}/decrease", consumes = "application/json")
  @ExceptionHandler({DecreaseAmountException.class})
  public void decreaseUserBalance(
    @PathVariable @UserValidator String userId,
    @Valid @TokenValidator @RequestBody DecreaseRequestDto decreaseRequestDto
  ){
    accountService.decreaseUserBalance(userId, decreaseRequestDto);
  }


}
