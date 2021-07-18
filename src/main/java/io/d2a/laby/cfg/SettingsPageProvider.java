package io.d2a.laby.cfg;

import java.util.List;
import javax.annotation.Nonnull;
import net.labymod.settings.elements.SettingsElement;

public interface SettingsPageProvider<T> {

  void fillSettings (
      @Nonnull final T obj,
      @Nonnull final List<SettingsElement> list
  ) throws IllegalAccessException;

}
