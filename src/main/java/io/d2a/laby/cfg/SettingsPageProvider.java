package io.d2a.laby.cfg;

import io.d2a.laby.cfg.exceptions.WrapException;
import io.d2a.laby.cfg.exceptions.WrapperNotFoundException;
import java.util.List;
import javax.annotation.Nonnull;
import net.labymod.settings.elements.SettingsElement;

public interface SettingsPageProvider<T> {

  void fillSettings (
      @Nonnull final T obj,
      @Nonnull final List<SettingsElement> list
  ) throws IllegalAccessException, WrapException;

}
