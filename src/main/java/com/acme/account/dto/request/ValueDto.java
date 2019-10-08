package com.acme.account.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@AllArgsConstructor
@Getter
@Builder(toBuilder = true)
@NoArgsConstructor
public class ValueDto {

  @NotNull
  @Positive(message = "Decrease value must be greater than 0")
  private float value;

}
