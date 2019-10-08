package com.acme.account.dto.response;

import com.acme.account.dto.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Getter
@Builder(toBuilder = true)
@NoArgsConstructor
public class TransactionDto {

  private TransactionType type;
  private float value;

}
