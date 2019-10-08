package com.acme.account.exception;

public class BadTokenException extends RuntimeException {

  private static final String MESSAGE = "User was not authorized to do this operation!";

  public BadTokenException() {
    super(MESSAGE);
  }
}
