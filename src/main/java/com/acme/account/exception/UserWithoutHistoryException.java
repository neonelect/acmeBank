package com.acme.account.exception;

public class UserWithoutHistoryException extends RuntimeException {

  private static final String MESSAGE = "User have no transaction history!";

  public UserWithoutHistoryException() {
    super(MESSAGE);
  }
}
