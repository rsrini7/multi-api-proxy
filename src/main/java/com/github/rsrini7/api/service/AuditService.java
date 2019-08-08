package com.github.rsrini7.api.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;

@Slf4j
@Service
public class AuditService {

    public void logMessage(String message) {

    String extendedMessage = MessageFormat.format("{0} [invoked by User ''{1}'' with id ''{2}'']", message, "user-name", "user-id");
    log.info(extendedMessage);
  }
}
