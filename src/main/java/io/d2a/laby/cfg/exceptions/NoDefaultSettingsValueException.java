package io.d2a.laby.cfg.exceptions;

import io.d2a.laby.cfg.annotations.Settings;
import java.lang.reflect.Field;

public class NoDefaultSettingsValueException extends SettingsParseException {

  private final Settings settings;
  private final Field field;

  public NoDefaultSettingsValueException(final Settings settings, final Field field) {
    super("Field " + field.getName() + " was annotated with @Setting, "
        + "but the provided config did not contain a value for this setting and no default value was found. "
        + "Annotate with @Default if you want to set the default value of the field to the config");

    this.settings = settings;
    this.field = field;
  }

  public Settings getSetting() {
    return settings;
  }

  public Field getField() {
    return field;
  }

}
