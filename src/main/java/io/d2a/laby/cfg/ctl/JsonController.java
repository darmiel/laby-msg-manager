package io.d2a.laby.cfg.ctl;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import io.d2a.laby.cfg.Dummy;
import io.d2a.laby.cfg.JsonProvider;
import io.d2a.laby.cfg.Lang;
import io.d2a.laby.cfg.annotations.Settings;
import io.d2a.laby.cfg.exceptions.EmptySettingsPathException;
import io.d2a.laby.cfg.exceptions.NoDefaultSettingsValueException;
import io.d2a.laby.cfg.exceptions.SettingsParseException;
import java.lang.reflect.Field;

public class JsonController<T> implements JsonProvider<T> {

  private static final Gson gson = new Gson();

  @Override
  public void readConfig(
      final JsonObject json,
      final T obj,
      final Runnable saveConfigAction
  ) throws SettingsParseException, IllegalAccessException, NoSuchFieldException {

    for (final Field field : obj.getClass().getDeclaredFields()) {
      // ignore dummies
      if (field.getType() == Dummy.class) {
        continue;
      }

      if (!field.isAnnotationPresent(Settings.class)) {
        continue;
      }
      final Settings settings = field.getAnnotation(Settings.class);

      String jsonKey;
      if (settings.value().trim().isEmpty()) {
        jsonKey = Lang.toPascalCase(field.getName());
      } else {
        jsonKey = Lang.getJsonKey(settings);
      }
      if (jsonKey.trim().isEmpty()) {
        throw new EmptySettingsPathException(field);
      }

      // Update Config
      // (adds default values)
      if (!json.has(jsonKey)) {
        // Get default value
        final Object defValue = field.get(obj);

        // no default value?
        // unacceptable...
        if (defValue == null) {
          throw new NoDefaultSettingsValueException(settings, field);
        }

        final JsonElement defJsonValue = gson.toJsonTree(defValue);
        json.add(jsonKey, defJsonValue);

        saveConfigAction.run();
      }

      final JsonElement jsonValue = json.get(jsonKey);

      Object fieldValue;
      if (jsonValue.isJsonPrimitive()) {
        // ugly ass hack.
        // please show me how to do this correctly.
        // this CANNOT be the only option
        final JsonPrimitive primitive = jsonValue.getAsJsonPrimitive();
        final Field valueField = JsonPrimitive.class.getDeclaredField("value");
        valueField.setAccessible(true);
        fieldValue = valueField.get(primitive);
      } else {
        fieldValue = gson.fromJson(jsonValue, field.getDeclaringClass());
      }

      field.setAccessible(true);
      field.set(obj, fieldValue);
    }
  }

}
