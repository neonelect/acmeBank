package com.acme.account.exception;

/**
 * Thrown when the token exists but is given with bad user
 */
public class BadTokenException extends RuntimeException {

  private static final String MESSAGE = "User was not authorized to do this operation!";

  public BadTokenException() {
    super(MESSAGE);
  }
}
