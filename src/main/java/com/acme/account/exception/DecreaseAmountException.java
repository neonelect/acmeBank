package com.acme.account.exception;

public class DecreaseAmountException extends RuntimeException {

  private static final String MESSAGE = "Insufficient founds!";

  public DecreaseAmountException() {
    super(MESSAGE);
  }
}
