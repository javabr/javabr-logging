package org.javabr.mule.logging.internal;

import java.io.InputStream;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.mule.runtime.api.metadata.TypedValue;
import org.mule.runtime.extension.api.annotation.Expression;
import org.mule.runtime.extension.api.annotation.param.Optional;
import org.mule.runtime.extension.api.annotation.param.Parameter;
import org.mule.runtime.extension.api.annotation.param.display.Placement;
import org.mule.runtime.extension.api.annotation.param.display.Summary;
import org.mule.runtime.extension.api.runtime.parameter.ParameterResolver;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "message",
    "timestamp",
    "correlationId",
    "level",
    "environment",
    "application",
    "content"
})

public class LogBody {

  @JsonProperty("message")
  @Parameter
  @Summary("Message to be logged")
  private String message;

  @JsonProperty("correlationId")
  @Parameter
  @Optional(defaultValue = "#[correlationId]")
  @Placement(tab = "Advanced")
  private String correlationId;

  @JsonProperty("content")
  @Parameter
  @Optional(defaultValue = "")
  @Summary("Add a dataweave expression.")
  private ParameterResolver<TypedValue<InputStream>> content;

  @JsonProperty("level")
  @Parameter
  @Optional(defaultValue = "INFO")
  @Summary("Log level")
  private Level level;

  @JsonProperty("timestamp")
  private String timestamp;

  @JsonProperty("environment")
  private String environment;

  @JsonProperty("application")
  @Expression
  private String application;

  @JsonProperty("application")
  public String getApplication() {
    return application;
  }

  @JsonProperty("application")
  public void setApplication(String application) {
    this.application = application;
  }

  @JsonProperty("timestamp")
  public String getTimestamp() {
    return timestamp;
  }

  @JsonProperty("timestamp")
  public void setTimestamp(String timestamp) {
    this.timestamp = timestamp;
  }

  @JsonProperty("environment")
  public String getEnvironment() {
    return environment;
  }

  @JsonProperty("environment")
  public void setEnvironment(String environment) {
    this.environment = environment;
  }

  @JsonProperty("correlationId")
  public String getCorrelationId() {
    return correlationId;
  }

  @JsonProperty("correlationId")
  public void setCorrelationId(String correlationId) {
    this.correlationId = correlationId;
  }

  @JsonProperty("message")
  public String getMessage() {
    return message;
  }

  @JsonProperty("message")
  public void setMessage(String message) {
    this.message = message;
  }

  @JsonProperty("content")
  public ParameterResolver<TypedValue<InputStream>> getContent() {
    return content;
  }

  @JsonProperty("content")
  public void setContent(ParameterResolver<TypedValue<InputStream>> content) {
    this.content = content;
  }

  @JsonProperty("level")
  public Level getLevel() {
    return level;
  }

  @JsonProperty("level")
  public void setLevel(Level level) {
    this.level = level;
  }

  @Override
  public String toString() {
    return org.apache.commons.lang3.builder.ToStringBuilder.reflectionToString(this);
  }

  @Override
  public int hashCode() {
    return new org.apache.commons.lang3.builder.HashCodeBuilder().append(correlationId).append(message).append(content)
        .append(level).toHashCode();
  }

  @Override
  public boolean equals(Object other) {
    if (other == this) {
      return true;
    }
    if ((other instanceof LogBody) == false) {
      return false;
    }
    LogBody rhs = ((LogBody) other);
    return new EqualsBuilder().append(correlationId, rhs.correlationId).append(message, rhs.message)
        .append(content, rhs.content).append(level, rhs.level)
        .isEquals();
  }
}
