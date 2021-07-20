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
    final String icon = settings.icon().trim();
    for (final Material mat : Material.values()) {
      if (mat.name().equalsIgnoreCase(icon)) {
        return new IconData(mat);
      }
    }
    return null;
  }

  static String formatName(final Settings settings) {
    return ModColor.createColors(settings.value());
  }

  ///

  Class<?>[] accepted(); // int.class, Integer.class, double.class, Double.class

  T wrap(final Settings settings, final Object defaultValue, final Consumer<V> changed);

}
