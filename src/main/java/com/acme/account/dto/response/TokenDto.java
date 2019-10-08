package com.acme.account.dto.response;

import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@Builder(toBuilder = true)
@NoArgsConstructor
public class TokenDto {

  private String token;

}
