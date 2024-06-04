package org.javabr.mule.logging.internal;

import static org.mule.runtime.extension.api.annotation.param.MediaType.ANY;

import java.io.InputStream;

import javax.inject.Inject;

import static org.javabr.mule.logging.api.dao.Level.*;
import org.javabr.mule.logging.api.dao.Level;

import org.mule.runtime.extension.api.annotation.param.MediaType;
import org.mule.runtime.extension.api.annotation.param.ParameterGroup;
import org.mule.runtime.extension.api.runtime.parameter.ParameterResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.node.ObjectNode;

import static org.mule.runtime.api.metadata.DataType.TEXT_STRING;

import org.javabr.mule.logging.api.dao.LogBody;
import org.mule.runtime.api.meta.model.operation.ExecutionType;
import org.mule.runtime.api.metadata.TypedValue;
import org.mule.runtime.api.transformation.TransformationService;
import org.mule.runtime.extension.api.annotation.execution.Execution;
import org.mule.runtime.extension.api.annotation.param.Config;
import org.mule.runtime.extension.api.annotation.param.Content;

/**
 * 
 */
public class JavabrloggingOperations {

  @Inject
  private TransformationService transformationService;

  private final static Logger LOGGER = LoggerFactory.getLogger(JavabrloggingOperations.class.getName());

  /**
   * 
   */
  @Execution(ExecutionType.BLOCKING)
  @MediaType(value = ANY, strict = false)
  public void log(@ParameterGroup(name = "Log") @Content LogBody logBody,
      @Config JavabrloggingConfiguration configuration) {
    LOGGER.debug("Starting log operation");

    // If not logable, stops here and saves some processing time.
    if (!isLogable(logBody.getLevel(), configuration.getLogConfig().getLogger()))
      return;

    LOGGER.debug("Logging log level %s", logBody.getLevel());
    logBody.setEnvironment(configuration.getLogConfig().getMuleEnvironmentName());
    logBody.setTimestamp(configuration.currentDatetime());
    logBody.setApplication(configuration.getLogConfig().getApplicationName());
    try {
      ObjectNode finalLog = configuration.getMapper().createObjectNode();
      finalLog.setAll((ObjectNode) configuration.getMapper().valueToTree(logBody));
      finalLog.remove("content");
      if (logBody.getContent() != null) {
        String content = parseField(logBody.getContent());
        if (content != null && !content.equals("")) {
          finalLog.put("content", content);
        }
      }
      String logStatement = configuration.getMapper().writerWithDefaultPrettyPrinter().writeValueAsString(finalLog);
      switch (logBody.getLevel()) {
        case TRACE:
          configuration.getLogConfig().getLogger().trace(logStatement);
          break;
        case DEBUG:
          configuration.getLogConfig().getLogger().debug(logStatement);
          break;
        case INFO:
          configuration.getLogConfig().getLogger().info(logStatement);
          break;
        case WARN:
          configuration.getLogConfig().getLogger().warn(logStatement);
          break;
        case ERROR:
          configuration.getLogConfig().getLogger().error(logStatement);
          break;
        default:
          break;
      }
    } catch (JsonProcessingException e) {
      LOGGER.error("Error Processing logging. Reson was " + e.getMessage());
    }
  }

  private Boolean isLogable(Level level, Logger logger) {
    switch (level) {
      case TRACE:
        return logger.isTraceEnabled();
      case DEBUG:
        return logger.isDebugEnabled();
      case INFO:
        return logger.isInfoEnabled();
      case WARN:
        return logger.isWarnEnabled();
      case ERROR:
        return logger.isErrorEnabled();
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
