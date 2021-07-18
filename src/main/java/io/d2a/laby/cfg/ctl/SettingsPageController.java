package io.d2a.laby.cfg.ctl;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import io.d2a.laby.cfg.SettingsPageProvider;
import io.d2a.laby.cfg.annotations.Header;
import io.d2a.laby.cfg.annotations.Setting;
import io.d2a.laby.cfg.wrapper.BooleanElementWrapper;
import io.d2a.laby.cfg.wrapper.NumberElementWrapper;
import io.d2a.laby.cfg.wrapper.SettingElementWrapper;
import io.d2a.laby.cfg.wrapper.StringElementWrapper;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import javax.annotation.Nonnull;
import net.labymod.settings.elements.SettingsElement;

public class SettingsPageController<T> implements SettingsPageProvider<T> {

  private final ListenerController lstCtl;

  public SettingsPageController(final ListenerController lstCtl) {
    this.lstCtl = lstCtl;
  }

  public static final List<SettingElementWrapper<? extends SettingsElement, ?>> WRAPPERS
      = new ArrayList<SettingElementWrapper<? extends SettingsElement, ?>>() {{
    add(new BooleanElementWrapper());
    add(new NumberElementWrapper());
    add(new StringElementWrapper());
  }};

  @Override
  public void fillSettings(
      @Nonnull final T obj,
      @Nonnull final List<SettingsElement> list
  ) throws IllegalAccessException {
    // clear previous elements
    list.clear();

    Map<String, List<SettingsElement>> elements = Maps.newHashMap();

    for (final Field field : obj.getClass().getDeclaredFields()) {
      if (!field.isAnnotationPresent(Setting.class)) {
        continue;
      }

      final Object value = field.get(obj);
      final Setting setting = field.getAnnotation(Setting.class);

      String header = "";
      if (field.isAnnotationPresent(Header.class)) {
        header = field.getAnnotation(Header.class).value().trim();
      }

      if (!elements.containsKey(header)) {
        elements.put(header, Lists.newArrayList());
      }
      final List<SettingsElement> el = elements.get(header);

      for (final SettingElementWrapper<? extends SettingsElement, ?> wrapper : WRAPPERS) {
        boolean accepted = false;
        for (final Class<?> clazz : wrapper.accepted()) {
          if (field.getType().isAssignableFrom(clazz)) {
            accepted = true;
            break;
          }
        }
        if (!accepted) {
          continue;
        }
        el.add(wrapper.wrap(setting, value, changedVal -> {
          // TODO: Fill me
          try {
            this.lstCtl.alert(setting.value(), null, changedVal);
          } catch (InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
          }
        }));
      }
    }

    for (final Entry<String, List<SettingsElement>> entry : elements.entrySet()) {
      // TODO: Add Header
      list.addAll(entry.getValue());
    }

  }

}
