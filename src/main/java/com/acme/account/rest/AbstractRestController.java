package com.acme.account.rest;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * Abstract class for controllers with response object creation methods
 */
class AbstractRestController {

  /**
   * Creates ok response
   *
   * @param body object passed in body
   * @param <T> class of the dto to be passed in the body
   * @return {@link ResponseEntity} object with given body and 200 status
   */
  final <T> ResponseEntity createOkResponse(T body){
    return createResponse(body, HttpStatus.OK);
  }

  /**
   * Creates response with given HTTP code
   *
   * @param body object passed in body
   * @param <T> class of the dto to be passed in the body
   * @return {@link ResponseEntity} object with given body and given status
   */
  final <T> ResponseEntity createResponse(T body, HttpStatus status){
    return new ResponseEntity<>(body, getHeaders(), status);
  }

  private HttpHeaders getHeaders(){
    HttpHeaders headers = new HttpHeaders();
    headers.set("Content-Type", "application/json");

    return headers;
  }
}
