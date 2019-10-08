package com.acme.account.dto.response;

import com.acme.account.dto.response.TransactionDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@Getter
@Builder(toBuilder = true)
@NoArgsConstructor
public class UserTransactionHistoryDto {

  private List<TransactionDto> transactions;

}
