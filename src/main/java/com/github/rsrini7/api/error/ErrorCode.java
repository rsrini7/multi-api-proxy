package com.github.rsrini7.api.error;

import lombok.Getter;

@Getter
public enum ErrorCode {
  VALIDATION_FAILED(101, "Validation Failed", "Validation Failed");

  private final int code;
  private final String title;
  private final String detail;

  ErrorCode(int code, String title, String detail) {
    this.code = code;
    this.title = title;
    this.detail = detail;
  }

  public String codeAsString() {
    return String.valueOf(code);
  }
}
