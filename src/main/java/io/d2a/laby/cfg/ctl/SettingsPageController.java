package io.d2a.laby.cfg.ctl;

import com.google.common.collect.Maps;
import io.d2a.laby.cfg.Dummy;
import io.d2a.laby.cfg.Lang;
import io.d2a.laby.cfg.SettingsPageProvider;
import io.d2a.laby.cfg.annotations.Header;
import io.d2a.laby.cfg.annotations.Settings;
import io.d2a.laby.cfg.exceptions.WrapException;
import io.d2a.laby.cfg.exceptions.WrapperNotFoundException;
import io.d2a.laby.cfg.wrapper.BooleanElementWrapper;
import io.d2a.laby.cfg.wrapper.DummyWrapper;
import io.d2a.laby.cfg.wrapper.NumberElementWrapper;
import io.d2a.laby.cfg.wrapper.SettingsElementWrapper;
import io.d2a.laby.cfg.wrapper.StringElementWrapper;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.labymod.settings.elements.HeaderElement;
import net.labymod.settings.elements.SettingsElement;

public class SettingsPageController<T> implements SettingsPageProvider<T> {

  private final ListenerController lstCtl;
  private final Map<String, Object> oldValues;

  public SettingsPageController(final ListenerController lstCtl) {
    this.lstCtl = lstCtl;
    this.oldValues = Maps.newConcurrentMap();
  }

  public static final List<SettingsElementWrapper<? extends SettingsElement, ?>> WRAPPERS
      = new ArrayList<SettingsElementWrapper<? extends SettingsElement, ?>>() {{
    add(new BooleanElementWrapper());
    add(new NumberElementWrapper());
    add(new StringElementWrapper());
  }};

  @Override
  public void fillSettings(
      @Nonnull final T obj,
      @Nonnull final List<SettingsElement> list
  ) throws IllegalAccessException, WrapException {

    // clear previous elements
    list.clear();

    for (final Field field : obj.getClass().getDeclaredFields()) {
      System.out.println("++ Field: " + field.getName() +
          " (" + field.getType().getSimpleName() + ")");

      /// Header
      if (field.isAnnotationPresent(Header.class)) {
        final String header = Lang.getFormattedName(field.getAnnotation(Header.class));
        list.add(new HeaderElement(header));
      }
      // Ignore Dummy
      if (field.getType().isAssignableFrom(Dummy.class)) {
        continue;
      }

      /// Settings
      if (!field.isAnnotationPresent(Settings.class)) {
        continue;
      }

      final Settings settings = field.getAnnotation(Settings.class);
      final Object value = field.get(obj); // get default value

      // find wrapper for {@link Settings.wrapper()} or field type
      final SettingsElementWrapper<? extends SettingsElement, ?> wrapper =
          this.findWrapper(settings, field);
      if (wrapper == null) {
        throw new WrapperNotFoundException("no wrapper for type " +
            field.getType().getSimpleName() + " found");
      }

      // Wrap @Setting-Annotation to {@link SettingsElement}
      final SettingsElement element = this.createSettingsElement(wrapper, settings, value);
      if (element == null) {
        throw new WrapException("field " + field.getName() +
            " could not be wrapped to SettingsElement");
      }
    }
  }

  private @Nullable
  SettingsElement createSettingsElement(
      final SettingsElementWrapper<? extends SettingsElement, ?> wrapper,
      final Settings settings,
      final Object defaultValue
  ) {
    final String listenerName = Lang.getListenerName(settings);

    return wrapper.wrap(
        settings,
        defaultValue,
        newValue -> {
          final Object oldValue = SettingsPageController.this.oldValues.get(listenerName);
          try {
            SettingsPageController.this.lstCtl.alert(listenerName, oldValue, newValue);
          } catch (InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
          }
          SettingsPageController.this.oldValues.put(listenerName, newValue);
        }
    );
  }

  private @Nullable
  SettingsElementWrapper<? extends SettingsElement, ?> findWrapper(
      final Settings settings,
      final Field field
  ) {
    for (final SettingsElementWrapper<? extends SettingsElement, ?> wrapper : WRAPPERS) {
      if (settings.wrapper() != DummyWrapper.class && wrapper.getClass() == settings.wrapper()) {
        return wrapper;
      }
      for (final Class<?> clazz : wrapper.accepted()) {
        if (field.getType().isAssignableFrom(clazz)) {
          return wrapper;
        }
      }
    }
    return null;
  }

}