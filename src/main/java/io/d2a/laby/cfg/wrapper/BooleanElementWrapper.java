package io.d2a.laby.cfg.wrapper;

import io.d2a.laby.cfg.annotations.Setting;
import net.labymod.settings.elements.BooleanElement;
import net.labymod.utils.Consumer;

public class BooleanElementWrapper implements SettingElementWrapper<BooleanElement, Boolean> {

  @Override
  public Class<?>[] accepted() {
    return new Class<?>[]{
        boolean.class,
        Boolean.class
    };
  }

  @Override
  public BooleanElement wrap(
      final Setting setting,
      final Object defaultValue,
      final Consumer<Boolean> changed
  ) {
    return new BooleanElement(
        SettingElementWrapper.formatName(setting),
        SettingElementWrapper.getIconData(setting),
        changed,
        (boolean) defaultValue
    );
  }

}
