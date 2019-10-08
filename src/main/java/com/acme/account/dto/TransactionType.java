package com.acme.account.dto;

import com.fasterxml.jackson.annotation.JsonValue;

public enum TransactionType {

  INCREASE,
  DECREASE
  ;

  @JsonValue
  public String toValue() {
    return this.name().toLowerCase();
  }

  }
