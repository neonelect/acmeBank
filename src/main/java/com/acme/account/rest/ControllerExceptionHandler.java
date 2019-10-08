package com.acme.account.rest;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Handler for returned errors
 */
@ControllerAdvice
public class ControllerExceptionHandler {

  /**
   * Modifies thrown errors
   *
   * @param ex exception thrown in runtime
   * @return list of pure messages
   */
  @ExceptionHandler(ConstraintViolationException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public List<String> handleValidationExceptions(ConstraintViolationException ex) {
    return ex.getConstraintViolations()
      .stream()
      .map(ConstraintViolation::getMessageTemplate)
      .collect(Collectors.toList());
  }

}
