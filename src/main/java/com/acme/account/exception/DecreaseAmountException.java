package com.acme.account.exception;

/**
 * Thrown when user doesn't have sufficient funds to be withdraw.
 */
public class DecreaseAmountException extends RuntimeException {

  private static final String MESSAGE = "Insufficient founds!";

  public DecreaseAmountException() {
    super(MESSAGE);
  }
}
