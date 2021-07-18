package io.d2a.laby.cfg.exceptions;

import java.lang.reflect.Field;

public class EmptySettingPathException extends SettingParseException {

  private final Field field;

  public EmptySettingPathException(final Field field) {
    super("Field " + field.getName() + " was annotated with @Setting, but `value` was empty");
    this.field = field;
  }

  public Field getField() {
    return field;
  }

}
