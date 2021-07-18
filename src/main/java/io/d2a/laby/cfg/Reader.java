package io.d2a.laby.cfg;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import io.d2a.laby.cfg.annotations.Default;
import io.d2a.laby.cfg.annotations.Setting;
import io.d2a.laby.cfg.exceptions.EmptySettingPathException;
import io.d2a.laby.cfg.exceptions.NoDefaultSettingValueException;
import io.d2a.laby.cfg.exceptions.SettingParseException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.labymod.api.LabyModAddon;

public class Reader<T> {

  private static final Gson gson = new Gson();

  private final Class<T> clazz;
  private final T obj;
  private final LabyModAddon addon;

  ///

  public Reader(
      @Nonnull LabyModAddon addon,
      @Nonnull final Class<T> clazz,
      @Nullable T obj)
      throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {

    this.addon = addon;
    this.clazz = clazz;

    // create new instance
    if (obj == null) {
      this.obj = clazz.getConstructor().newInstance();
    } else {
      this.obj = obj;
    }
  }

  public void parse()
      throws IllegalAccessException, SettingParseException, NoSuchFieldException {
    
    final JsonObject json = this.addon.getConfig();
    boolean update = false;

    for (final Field field : this.clazz.getDeclaredFields()) {
      if (!field.isAnnotationPresent(Setting.class)) {
        continue;
      }

      final Setting setting = field.getAnnotation(Setting.class);

      final String jsonKey = Lang.toPascalCase(setting.value());
      if (jsonKey.trim().isEmpty()) {
        throw new EmptySettingPathException(field);
      }

      // Update
      if (!json.has(jsonKey)) {
        if (!field.isAnnotationPresent(Default.class)) {
          throw new NoDefaultSettingValueException(setting, field);
        }

        // Get value
        final Object defValue = field.get(this.obj);
        final JsonElement defJsonValue = gson.toJsonTree(defValue);
        json.add(jsonKey, defJsonValue);

        // update -> true; save config after parsing
        update = true;

        // TODO: Remove me
        this.addon.saveConfig();
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
      field.set(this.obj, fieldValue);
    }

    if (update) {
      this.addon.saveConfig();
    }
  }

}
