package io.d2a.laby.cfg.wrapper;

import io.d2a.laby.cfg.annotations.Setting;
import net.labymod.settings.elements.StringElement;
import net.labymod.utils.Consumer;

public class StringElementWrapper implements SettingElementWrapper<StringElement, String> {

  @Override
  public Class<?>[] accepted() {
    return new Class[]{
        String.class
    };
  }

  @Override
  public StringElement wrap(
      final Setting setting,
      final Object defaultValue,
      final Consumer<String> changed
  ) {
   return new StringElement(
        SettingElementWrapper.formatName(setting),
        SettingElementWrapper.getIconData(setting),
        (String) defaultValue,
        changed
    );
  }
}
