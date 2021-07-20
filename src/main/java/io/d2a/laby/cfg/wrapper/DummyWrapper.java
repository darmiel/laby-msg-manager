package io.d2a.laby.cfg.wrapper;

import io.d2a.laby.cfg.Dummy;
import io.d2a.laby.cfg.annotations.Settings;
import net.labymod.settings.elements.HeaderElement;
import net.labymod.utils.Consumer;

public class DummyWrapper implements SettingsElementWrapper<HeaderElement, Dummy> {

  @Override
  public Class<?>[] accepted() {
    return new Class[0];
  }

  @Override
  public HeaderElement wrap(
      final Settings settings,
      final Object defaultValue,
      final Consumer<Dummy> changed
  ) {
    return new HeaderElement("not set");
  }

}
