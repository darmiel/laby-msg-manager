package io.d2a.laby.cfg.wrapper;

import io.d2a.laby.cfg.annotations.Settings;
import javax.annotation.Nullable;
import net.labymod.settings.elements.ControlElement.IconData;
import net.labymod.utils.Consumer;
import net.labymod.utils.Material;
import net.labymod.utils.ModColor;

public interface SettingsElementWrapper<T, V> {

  @Nullable
  static IconData getIconData(final Settings settings) {
    if (settings.icon() == Material.AIR) {
      return null;
    }
    return new IconData(settings.icon());
  }

  static String formatName(final Settings settings) {
    return ModColor.createColors(settings.value());
  }

  ///

  Class<?>[] accepted(); // int.class, Integer.class, double.class, Double.class

  T wrap(final Settings settings, final Object defaultValue, final Consumer<V> changed);

}
