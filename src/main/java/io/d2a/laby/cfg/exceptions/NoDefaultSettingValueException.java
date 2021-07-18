package io.d2a.laby.cfg.exceptions;

import io.d2a.laby.cfg.annotations.Setting;
import java.lang.reflect.Field;

public class NoDefaultSettingValueException extends SettingParseException {

  private final Setting setting;
  private final Field field;

  public NoDefaultSettingValueException(final Setting setting, final Field field) {
    super("Field " + field.getName() + " was annotated with @Setting, "
        + "but the provided config did not contain a value for this setting and no default value was found. "
        + "Annotate with @Default if you want to set the default value of the field to the config");

    this.setting = setting;
    this.field = field;
  }

  public Setting getSetting() {
    return setting;
  }

  public Field getField() {
    return field;
  }

}
