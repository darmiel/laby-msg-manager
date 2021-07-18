package io.d2a.laby.cfg;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import io.d2a.laby.cfg.annotations.listener.ListenSettingsChange;
import io.d2a.laby.cfg.annotations.listener.NewVal;
import io.d2a.laby.cfg.annotations.listener.OldVal;
import io.d2a.laby.cfg.annotations.listener.VarName;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class ListenerController<T> implements ListenerControllerProvider {

  private final Map<String, List<ListenerMethod>> listeners;

  ///

  public ListenerController() {
    this.listeners = Maps.newConcurrentMap();
  }

  @Override
  public void alert(
      @Nonnull String var,
      @Nullable final Object oldValue,
      @Nullable final Object newValue
  ) throws InvocationTargetException, IllegalAccessException {

    var = var.toLowerCase();

    if (!this.listeners.containsKey(var)) {
      return;
    }

    for (final ListenerMethod m : this.listeners.get(var)) {
      List<Object> parameters = new ArrayList<>();
      final Method method = m.method;

      // Add parameters
      for (final Parameter parameter : method.getParameters()) {
        if (parameter.isAnnotationPresent(NewVal.class)) {
          parameters.add(newValue);
        } else if (parameter.isAnnotationPresent(OldVal.class)) {
          parameters.add(oldValue);
        } else if (parameter.isAnnotationPresent(VarName.class)) {
          parameters.add(var);
        } else {
          parameters.add(null);
          System.out.println("msgman :: WARN :: parameter " + parameter.getName() + " is invalid.");
        }
      }

      // Invoke listener method
      method.invoke(m.instance, parameters.toArray());
    }

  }

  @Override
  public void registerListeners(@Nonnull final Object ... obj) {
    for (final Object o : obj) {
      for (final Method method : o.getClass().getDeclaredMethods()) {
        if (!method.isAnnotationPresent(ListenSettingsChange.class)) {
          continue;
        }

        registerListener(
            o,
            method.getAnnotation(ListenSettingsChange.class),
            method
        );

        System.out.println("msgman :: registered method " + method.getName() +
            " for class " + o.getClass().getSimpleName());
      }
    }
  }

  ///

  private void registerListener(
      @Nullable final Object instance,
      @Nonnull final ListenSettingsChange lann,
      @Nonnull final Method method) {

    String var = lann.value().toLowerCase();

    if (!this.listeners.containsKey(var)) {
      this.listeners.put(var, Lists.newArrayList());
    }

    final List<ListenerMethod> m = this.listeners.get(var);
    m.add(new ListenerMethod(instance, lann, method));

    // sort by order-weight
    m.sort((o1, o2) -> {
      int w1 = o1.lann.priority().weight,
          w2 = o2.lann.priority().weight;
      return Integer.compare(w1, w2);
    });
  }

}
