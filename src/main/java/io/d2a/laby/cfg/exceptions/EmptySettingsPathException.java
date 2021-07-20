package io.d2a.laby.cfg.exceptions;

import java.lang.reflect.Field;

public class EmptySettingsPathException extends SettingsParseException {

  private final Field field;

  public EmptySettingsPathException(final Field field) {
    super("Field " + field.getName() + " was annotated with @Setting, but `value` was empty");
    this.field = field;
  }

  public Field getField() {
    return field;
  }

}
