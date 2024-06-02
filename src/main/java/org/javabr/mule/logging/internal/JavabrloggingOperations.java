package org.javabr.mule.logging.internal;

import static org.mule.runtime.extension.api.annotation.param.MediaType.ANY;

import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.inject.Inject;

import org.mule.runtime.extension.api.annotation.param.MediaType;
import org.mule.runtime.extension.api.annotation.param.ParameterGroup;
import org.mule.runtime.extension.api.runtime.parameter.ParameterResolver;
import org.mule.runtime.module.artifact.api.classloader.MuleArtifactClassLoader;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.node.ObjectNode;

import static org.mule.runtime.api.metadata.DataType.TEXT_STRING;

import org.mule.runtime.api.metadata.TypedValue;
import org.mule.runtime.api.transformation.TransformationService;
import org.mule.runtime.extension.api.annotation.param.Config;
import org.mule.runtime.extension.api.annotation.param.Connection;
import org.mule.runtime.extension.api.annotation.param.Content;

/**
 * 
 */
public class JavabrloggingOperations {

  @Inject
  private TransformationService transformationService;

  private final static Logger LOGGER = Logger.getLogger(JavabrloggingOperations.class.getName());

  /**
   * 
   */
  @MediaType(value = ANY, strict = false)
  public void log(@ParameterGroup(name = "Log") @Content LogBody logBody,
      @Config JavabrloggingConfiguration configuration,
      @Connection JavabrloggingConnection connection) {
    LOGGER.log(Level.FINER, "Starting log operation");
    if (!isLogable(logBody.getLevel(), connection.getLogger()))
      return;
    LOGGER.log(Level.FINER, "Logging log level %s", logBody.getLevel());
    logBody.setEnvironment(connection.getEnvironment());
    logBody.setTimestamp(connection.currentDatetime()); // MuleArtifactClassLoader

    logBody.setApplication(connection.getApplication());

    try {
      ObjectNode finalLog = connection.getMapper().createObjectNode();
      finalLog.setAll((ObjectNode) connection.getMapper().valueToTree(logBody));
      finalLog.remove("content");
      if (logBody.getContent() != null) {
        String content = parseField(logBody.getContent());
        finalLog.put("content", content);
      }
      String logStatement = connection.getMapper().writerWithDefaultPrettyPrinter().writeValueAsString(finalLog);
      switch (logBody.getLevel()) {
        case TRACE:
          connection.getLogger().log(Level.FINEST, logStatement);
          break;
        case DEBUG:
          connection.getLogger().log(Level.FINER, logStatement);
          break;
        case INFO:
          connection.getLogger().log(Level.INFO, logStatement);
          break;
        case WARN:
          connection.getLogger().log(Level.WARNING, logStatement);
          break;
        case ERROR:
          connection.getLogger().log(Level.SEVERE, logStatement);
          break;
        default:
          break;
      }
    } catch (JsonProcessingException e) {
      LOGGER.severe("Error Processing logging. Reson was " + e.getMessage());
    }
  }

  private Boolean isLogable(org.javabr.mule.logging.internal.Level level, Logger logger) {
    switch (level) {
      case TRACE:
        return logger.isLoggable(Level.FINEST);
      case DEBUG:
        return logger.isLoggable(Level.FINE);
      case INFO:
        return logger.isLoggable(Level.INFO);
      case WARN:
        return logger.isLoggable(Level.WARNING);
      case ERROR:
        return logger.isLoggable(Level.SEVERE);
    }
    return false;
  }

  private String parseField(ParameterResolver<TypedValue<InputStream>> param) {
    TypedValue<InputStream> typedValue = (TypedValue<InputStream>) param.resolve();
    if (typedValue.getValue() != null) {
      String parsed = (String) transformationService.transform(typedValue.getValue(), typedValue.getDataType(),
          TEXT_STRING);
      return parsed;
    }
    return null;
  }

}
