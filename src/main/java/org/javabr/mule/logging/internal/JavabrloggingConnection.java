package org.javabr.mule.logging.internal;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.logging.Logger;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;

/**
 * This class represents an extension connection just as example (there is no
 * real connection with anything here c:).
 */
public final class JavabrloggingConnection {

  private final String id;
  private final String environment;

  private Logger logger = null;

  private DateFormat dataFormater = new SimpleDateFormat(
      "yyyy-MM-dd HH:mm:ss:SSSZ");

  private final ObjectMapper mapper = new ObjectMapper()
      .setSerializationInclusion(JsonInclude.Include.NON_NULL);

  private String applicationName;

  public JavabrloggingConnection(String applicationName, String artifactId, String environment) {
    this.id = applicationName + ":" + artifactId + ":" + environment;
    this.environment = environment;
    this.applicationName = applicationName;
    this.logger = Logger.getLogger(artifactId);
    this.mapper.registerModule(new Jdk8Module());
  }

  public String currentDatetime() {
    return dataFormater.format(System.currentTimeMillis());
  }

  public ObjectMapper getMapper() {
    return mapper;
  }

  public String getId() {
    return id;
  }

  public String getEnvironment() {
    return environment;
  }

  public Logger getLogger() {
    return logger;
  }

  public String getApplication() {
    return applicationName;
  }

  public void invalidate() {

  }
}
