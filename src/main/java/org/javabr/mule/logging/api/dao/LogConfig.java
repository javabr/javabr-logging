package org.javabr.mule.logging.api.dao;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.mule.runtime.extension.api.annotation.param.Optional;
import org.mule.runtime.extension.api.annotation.param.Parameter;
import org.mule.runtime.extension.api.annotation.param.display.DisplayName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "applicationName",
    "artifactId",
    "muleEnvironmentName"
})
public class LogConfig {

  private static Logger LOGGER;

  @Parameter
  @DisplayName("POM ArtifactId")
  private String artifactId;

  @Parameter
  @DisplayName("Mule Env Name")
  @Optional(defaultValue = "${mule.env}")
  private String muleEnvironmentName;

  @Parameter
  @DisplayName("Mule Application Name")
  private String applicationName;

  public void setApplicationName(String applicationName) {
    this.applicationName = applicationName;
  }

  @JsonProperty("applicationName")
  public String getApplicationName() {
    return applicationName;
  }

  @JsonProperty("applicationName")
  public void setMuleEnvironmentName(String muleEnvironmentName) {
    this.muleEnvironmentName = muleEnvironmentName;
  }

  @JsonProperty("muleEnvironmentName")
  public String getMuleEnvironmentName() {
    return muleEnvironmentName;
  }

  @JsonProperty("artifactId")
  public void setArtifactId(String artifactId) {
    this.artifactId = artifactId;
  }

  @JsonProperty("artifactId")
  public String getArtifactId() {
    return artifactId;
  }

  public Logger getLogger() {
    if (LOGGER == null) {
      LOGGER = LoggerFactory.getLogger(artifactId);
    }
    return LOGGER;
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this);
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder().append(applicationName).append(artifactId).append(muleEnvironmentName).toHashCode();
  }

  @Override
  public boolean equals(Object other) {
    if (other == this) {
      return true;
    }
    if ((other instanceof LogConfig) == false) {
      return false;
    }
    LogConfig rhs = ((LogConfig) other);
    return new EqualsBuilder().append(applicationName, rhs.applicationName).append(artifactId, rhs.artifactId)
        .append(muleEnvironmentName, rhs.muleEnvironmentName).isEquals();
  }

}
