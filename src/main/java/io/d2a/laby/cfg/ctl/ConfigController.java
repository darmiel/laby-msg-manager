package io.d2a.laby.cfg.ctl;

import io.d2a.laby.cfg.SettingsPageProvider;
import io.d2a.laby.cfg.exceptions.SettingsParseException;
import io.d2a.laby.cfg.wrapper.SettingsElementWrapper;
import java.lang.reflect.InvocationTargetException;
import java.util.Optional;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.labymod.api.LabyModAddon;
import net.labymod.settings.elements.SettingsElement;

public class ConfigController<T> {

  private final T obj;
  private final LabyModAddon addon;

  /// Controller
  private final ListenerController lstCtl;
  private final JsonController<T> jsonCtl;
  private final SettingsPageController<T> pageCtl;

  public ConfigController(
      final LabyModAddon addon,
      final Class<T> clazz,
      final T obj
  ) throws NoSuchMethodException, IllegalAccessException,
      InvocationTargetException, InstantiationException {

    if (obj == null) {
      this.obj = clazz.getConstructor().newInstance(); // create new instance of config class
    } else {
      this.obj = obj;
    }

    this.addon = addon;
    this.lstCtl = new ListenerController();
    this.jsonCtl = new JsonController<>();
    this.pageCtl = new SettingsPageController<>(this.lstCtl);
  }

  ///

  public static <T> Optional<ConfigController<T>> fromUnsafe(
      @Nonnull final LabyModAddon addon,
      @Nonnull final Class<T> configClass,
      @Nullable final T obj) {

    try {
      return Optional.of(new ConfigController<T>(
          addon,
          configClass,
          obj
      ));
    } catch (Exception exception) {
      exception.printStackTrace();
    }

    return Optional.empty();
  }
  /// Getters & Setters
  public SettingsPageProvider<T> getPageCtl() {
    return pageCtl;
  }

  public JsonController<T> getJsonCtl() {
    return jsonCtl;
  }

  public ListenerController getListenerCtl() {
    return lstCtl;
  }

  public T getObj() {
    return obj;
  }

  /// Aliases

  public void readConfig()
      throws SettingsParseException, IllegalAccessException, NoSuchFieldException {
    this.jsonCtl.readConfig(
        this.addon.getConfig(),
        this.obj,
        addon::saveConfig
    );
  }

  public ConfigController<T> registerAll(final Object... obj) {
    this.lstCtl.registerListeners(obj);
    return this;
  }

  public ConfigController<T> registerWrapper(
      @Nonnull final SettingsElementWrapper<? extends SettingsElement, ?> wrapper
  ) {
    this.pageCtl.registerWrapper(wrapper);
    return this;
  }


}
