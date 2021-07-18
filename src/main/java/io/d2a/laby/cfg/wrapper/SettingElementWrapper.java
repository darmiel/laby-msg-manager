package io.d2a.laby.cfg.wrapper;

import io.d2a.laby.cfg.annotations.Setting;
import javax.annotation.Nullable;
import net.labymod.settings.elements.ControlElement.IconData;
import net.labymod.settings.elements.SettingsElement;
import net.labymod.utils.Consumer;
import net.labymod.utils.Material;
import net.labymod.utils.ModColor;

public interface SettingElementWrapper<T, V> {

  Class<?>[] accepted(); // int.class, Integer.class, double.class, Double.class

  T wrap(final Setting setting, final Object defaultValue, final Consumer<V> changed);

  ///

  @Nullable
  static IconData getIconData(final Setting setting) {
    final String icon = setting.icon().trim();
    for (final Material mat : Material.values()) {
      if (mat.name().equalsIgnoreCase(icon)) {
        return new IconData(mat);
      }
    }
    return null;
  }

  static String formatName(final Setting setting) {
    return ModColor.cl(setting.value());
  }

}
