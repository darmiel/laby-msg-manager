package io.d2a.laby.cfg.wrapper;

import io.d2a.laby.cfg.annotations.Setting;
import net.labymod.settings.elements.NumberElement;
import net.labymod.utils.Consumer;

public class NumberElementWrapper implements SettingElementWrapper<NumberElement, Integer> {

  @Override
  public Class<?>[] accepted() {
    return new Class[]{
        int.class,
        Integer.class
    };
  }

  @Override
  public NumberElement wrap(
      final Setting setting,
      final Object defaultValue,
      final Consumer<Integer> changed
  ) {
    final NumberElement element = new NumberElement(
        SettingElementWrapper.formatName(setting),
        SettingElementWrapper.getIconData(setting),
        (int) defaultValue
    );
    element.addCallback(changed);
    return element;
  }
}
