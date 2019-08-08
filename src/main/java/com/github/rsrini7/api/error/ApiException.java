package com.github.rsrini7.api.error;

import lombok.Getter;
import lombok.ToString;

import java.util.Arrays;

@Getter
@ToString
public class ApiException extends RuntimeException {

  private final Error[] errors;

  public ApiException(Error error) {
    this(new Error[]{error});
  }

  public ApiException(Error[] errors) {
    super(Arrays.toString(errors));
    this.errors = errors;
  }
}
