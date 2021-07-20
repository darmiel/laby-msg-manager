package io.d2a.laby.cfg.wrapper;

import io.d2a.laby.cfg.annotations.Settings;
import net.labymod.settings.elements.StringElement;
import net.labymod.utils.Consumer;

public class StringElementWrapper implements SettingsElementWrapper<StringElement, String> {

  @Override
  public Class<?>[] accepted() {
    return new Class[]{
        String.class
    };
  }

  @Override
  public StringElement wrap(
      final Settings settings,
      final Object defaultValue,
      final Consumer<String> changed
  ) {
    return new StringElement(
        SettingsElementWrapper.formatName(settings),
        SettingsElementWrapper.getIconData(settings),
        (String) defaultValue,
        changed
    );
  }
}
