package io.d2a.laby.cfg.ctl;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import io.d2a.laby.cfg.JsonProvider;
import io.d2a.laby.cfg.Lang;
import io.d2a.laby.cfg.annotations.Default;
import io.d2a.laby.cfg.annotations.Setting;
import io.d2a.laby.cfg.exceptions.EmptySettingPathException;
import io.d2a.laby.cfg.exceptions.NoDefaultSettingValueException;
import io.d2a.laby.cfg.exceptions.SettingParseException;
import java.lang.reflect.Field;
import net.labymod.utils.ModColor;

public class JsonController<T> implements JsonProvider<T> {

  private static final Gson gson = new Gson();

  @Override
  public void readConfig(
      final JsonObject json,
      final T obj,
      final Runnable saveConfigAction
  ) throws SettingParseException, IllegalAccessException, NoSuchFieldException {

    for (final Field field : obj.getClass().getDeclaredFields()) {
      if (!field.isAnnotationPresent(Setting.class)) {
        continue;
      }

      final Setting setting = field.getAnnotation(Setting.class);

      String jsonKey;
      if (setting.value().trim().isEmpty()) {
        jsonKey = Lang.toPascalCase(field.getName());
      } else {
        jsonKey = Lang.toPascalCase(setting.value());
      }
      jsonKey = ModColor.removeColor(ModColor.cl(jsonKey)); // remove color
      if (jsonKey.trim().isEmpty()) {
        throw new EmptySettingPathException(field);
      }

      // Update Config
      // (adds default values)
      if (!json.has(jsonKey)) {
        if (!field.isAnnotationPresent(Default.class)) {
          throw new NoDefaultSettingValueException(setting, field);
        }

        // Get value
        final Object defValue = field.get(obj);
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
