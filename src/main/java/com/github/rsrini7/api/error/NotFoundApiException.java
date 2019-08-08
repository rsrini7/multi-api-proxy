package com.github.rsrini7.api.error;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class NotFoundApiException extends ApiException {
  public NotFoundApiException(Error error) {
    super(error);
  }

  public NotFoundApiException(Error[] errors) {
    super(errors);
  }
}
