package org.javabr.mule.logging.internal;

import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum Level {

  DEBUG("DEBUG"),
  TRACE("TRACE"),
  INFO("INFO"),
  WARN("WARN"),
  ERROR("ERROR");

  private final String value;
  private final static Map<String, Level> CONSTANTS = new HashMap<String, Level>();

  static {
    for (Level c : values()) {
      CONSTANTS.put(c.value, c);
    }
  }

  private Level(String value) {
    this.value = value;
  }

  @Override
  public String toString() {
    return this.value;
  }

  @JsonValue
  public String value() {
    return this.value;
  }

  @JsonCreator
  public static Level fromValue(String value) {
    Level constant = CONSTANTS.get(value);
    if (constant == null) {
      throw new IllegalArgumentException(value);
    } else {
      return constant;
    }
  }

}
