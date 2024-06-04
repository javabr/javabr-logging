package org.javabr.mule.logging.internal;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.javabr.mule.logging.api.dao.LogConfig;
import org.mule.runtime.api.meta.ExpressionSupport;
import org.mule.runtime.extension.api.annotation.Expression;
import org.mule.runtime.extension.api.annotation.Operations;
import org.mule.runtime.extension.api.annotation.param.Parameter;
import org.mule.runtime.extension.api.annotation.param.ParameterGroup;
import org.mule.runtime.extension.api.annotation.param.display.Placement;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;

/**
 * This class represents an extension configuration, values set in this class
 * are commonly used across multiple
 * operations since they represent something core from the extension.
 */
@Operations(JavabrloggingOperations.class)
// @ConnectionProviders(JavabrloggingConnectionProvider.class)
public class JavabrloggingConfiguration {

  @JsonProperty("logConfig")
  @Parameter
  @Expression(ExpressionSupport.NOT_SUPPORTED)
  @Placement(tab = "General", order = 1)
  @ParameterGroup(name = "Log Settings")
  private LogConfig logConfig;

  @JsonProperty("logConfig")
  public LogConfig getLogConfig() {
    return logConfig;
  }

  public void setLogConfig(LogConfig logConfig) {
    this.logConfig = logConfig;
  }

  public String currentDatetime() {
    return dataFormater.format(System.currentTimeMillis());
  }

  private final ObjectMapper mapper = new ObjectMapper()
      .setSerializationInclusion(JsonInclude.Include.NON_NULL);

  public JavabrloggingConfiguration() {
    this.mapper.registerModule(new Jdk8Module());
  }

  public ObjectMapper getMapper() {
    return mapper;
  }

  private DateFormat dataFormater = new SimpleDateFormat(
      "yyyy-MM-dd HH:mm:ss:SSS'Z'");

}
