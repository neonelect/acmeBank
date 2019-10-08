package com.acme.account.rest;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

class AbstractRestController {

  final <T> ResponseEntity createOkResponse(T body){
    return createResponse(body, HttpStatus.OK);
  }

  final <T> ResponseEntity createResponse(T body, HttpStatus status){
    return new ResponseEntity<>(body, getHeaders(), status);
  }

  private HttpHeaders getHeaders(){
    HttpHeaders headers = new HttpHeaders();
    headers.set("Content-Type", "application/json");

    return headers;
  }
}
