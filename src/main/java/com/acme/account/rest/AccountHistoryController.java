package com.acme.account.rest;

import com.acme.account.dto.response.UserTransactionHistoryDto;
import com.acme.account.exception.UserWithoutHistoryException;
import com.acme.account.service.AccountService;
import com.acme.account.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/history")
@Validated
public class AccountHistoryController extends AbstractRestController {

  @Autowired
  private AccountService accountService;

  @GetMapping(value = "/user/{userId}", produces = "application/json")
  @ExceptionHandler({UserWithoutHistoryException.class})
  public ResponseEntity getUserTransactionHistory(@PathVariable @UserValidator String userId){
    return createOkResponse(
      UserTransactionHistoryDto.builder().transactions(accountService.getUserTransactionHistory(userId)).build()
    );
  }

}
