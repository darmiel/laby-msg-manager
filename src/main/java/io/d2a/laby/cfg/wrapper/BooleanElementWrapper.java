package io.d2a.laby.cfg.wrapper;

import io.d2a.laby.cfg.annotations.Settings;
import net.labymod.settings.elements.BooleanElement;
import net.labymod.utils.Consumer;

public class BooleanElementWrapper implements SettingsElementWrapper<BooleanElement, Boolean> {

  @Override
  public Class<?>[] accepted() {
    return new Class<?>[]{
        boolean.class,
        Boolean.class
    };
  }

  @Override
  public BooleanElement wrap(
      final Settings settings,
      final Object defaultValue,
      final Consumer<Boolean> changed
  ) {
    return new BooleanElement(
        SettingsElementWrapper.formatName(settings),
        SettingsElementWrapper.getIconData(settings),
        changed,
        (boolean) defaultValue
    );
  }

}
