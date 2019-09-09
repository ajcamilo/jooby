/**
 * Jooby https://jooby.io
 * Apache License Version 2.0 https://jooby.io/LICENSE.txt
 * Copyright 2014 Edgar Espina
 */
package io.jooby.internal.converter;

import io.jooby.spi.ValueConverter;

import com.typesafe.config.ConfigException;
import com.typesafe.config.ConfigFactory;
import com.typesafe.config.ConfigValueFactory;

import java.time.Duration;
import java.time.format.DateTimeParseException;
import java.util.concurrent.TimeUnit;

public class DurationConverter implements ValueConverter {
  @Override public boolean supports(Class type) {
    return type == Duration.class;
  }

  @Override public Object convert(Class type, String value) {
    try {
      return Duration.parse(value);
    } catch (DateTimeParseException x) {
      try {
        long duration = ConfigFactory.empty().withValue("d", ConfigValueFactory.fromAnyRef(value))
            .getDuration("d", TimeUnit.MILLISECONDS);
        return Duration.ofMillis(duration);
      } catch (ConfigException ignored) {
        throw x;
      }
    }
  }
}
