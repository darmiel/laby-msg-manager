package io.d2a.laby.cfg.wrapper;

import io.d2a.laby.cfg.annotations.Settings;
import net.labymod.settings.elements.NumberElement;
import net.labymod.utils.Consumer;

public class NumberElementWrapper implements SettingsElementWrapper<NumberElement, Integer> {

  @Override
  public Class<?>[] accepted() {
    return new Class[]{
        int.class,
        Integer.class
    };
  }

  @Override
  public NumberElement wrap(
      final Settings settings,
      final Object defaultValue,
      final Consumer<Integer> changed
  ) {
    final NumberElement element = new NumberElement(
        SettingsElementWrapper.formatName(settings),
        SettingsElementWrapper.getIconData(settings),
        (int) defaultValue
    );
    element.addCallback(changed);
    return element;
  }
}
