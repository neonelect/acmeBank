package com.acme.account.exception;

/**
 * Thrown when user doesn't have a history
 */
public class UserWithoutHistoryException extends RuntimeException {

  private static final String MESSAGE = "User have no transaction history!";

  public UserWithoutHistoryException() {
    super(MESSAGE);
  }
}
