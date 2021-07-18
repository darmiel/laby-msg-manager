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
import java.util.Optional;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.labymod.api.LabyModAddon;

public class ConfigController<T> extends ListenerController<T> {

  private static final Gson gson = new Gson();

  private final Class<T> clazz;
  private final T obj;
  private final LabyModAddon addon;

  ///

  public static <T> Optional<ConfigController<T>> fromUnsafe(
      @Nonnull final LabyModAddon addon,
      @Nonnull final Class<T> configClass) {

    try {
      return Optional.of(new ConfigController<>(addon, configClass, null));
    } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException e) {
      e.printStackTrace();
    }

    return Optional.empty();
  }

  public ConfigController(
      @Nonnull final LabyModAddon addon,
      @Nonnull final Class<T> clazz,
      @Nullable T obj)
      throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
    super();

    this.addon = addon;
    this.clazz = clazz;

    // create new instance
    if (obj == null) {
      this.obj = clazz.getConstructor().newInstance();
    } else {
      this.obj = obj;
    }
  }

  /**
   * Returns a class of type {@link T} with the read values from the config, if <code>parse()</code>
   * was called previously
   *
   * @return Config Class
   */
  public T getObj() {
    return obj;
  }

  /**
   * Platz für Notizen
   *
   * @throws IllegalAccessException a
   * @throws SettingParseException  a
   * @throws NoSuchFieldException   a
   */
  public void parse()
      throws IllegalAccessException, SettingParseException, NoSuchFieldException {

    final JsonObject json = this.addon.getConfig();
    boolean update = false;

    for (final Field field : this.clazz.getDeclaredFields()) {
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

  public void parseUnsafe() {
    try {
      this.parse();
    } catch (IllegalAccessException | SettingParseException | NoSuchFieldException e) {
      e.printStackTrace();
    }
  }

  /**
   * Alias for {@link ListenerController#registerListeners(Object...)} for method chaining
   *
   * @param obj Instances
   * @return this
   */
  public ConfigController<T> registerAll(@Nonnull final Object... obj) {
    this.registerListeners(obj);
    return this;
  }

}
