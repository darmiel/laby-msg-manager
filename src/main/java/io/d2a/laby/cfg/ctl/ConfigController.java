package io.d2a.laby.cfg.ctl;

import com.google.gson.JsonObject;
import io.d2a.laby.cfg.JsonProvider;
import io.d2a.laby.cfg.ListenerProvider;
import io.d2a.laby.cfg.SettingsPageProvider;
import io.d2a.laby.cfg.exceptions.SettingParseException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
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
  private final SettingsPageProvider<T> pageCtl;


  public ConfigController(
      final LabyModAddon addon,
      final Class<T> clazz
  ) throws NoSuchMethodException, IllegalAccessException,
      InvocationTargetException, InstantiationException {

    this.addon = addon;
    this.obj = clazz.getConstructor().newInstance(); // create new instance of config class

    this.lstCtl = new ListenerController();
    this.jsonCtl = new JsonController<>();
    this.pageCtl = new SettingsPageController<>(this.lstCtl);
  }

  ///

  public static <T> Optional<ConfigController<T>> fromUnsafe(
      @Nonnull final LabyModAddon addon,
      @Nonnull final Class<T> configClass) {

    try {
      return Optional.of(new ConfigController<T>(
          addon,
          configClass
      ));
    } catch (Exception exception) {
      exception.printStackTrace();
    }

    return Optional.empty();
  }

  ///

  public void readConfig() throws SettingParseException, IllegalAccessException, NoSuchFieldException {
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

  public SettingsPageProvider<T> getPageCtl() {
    return pageCtl;
  }

  public T getObj() {
    return obj;
  }
}
