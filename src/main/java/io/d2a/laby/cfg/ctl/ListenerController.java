package io.d2a.laby.cfg.ctl;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import io.d2a.laby.cfg.ListenerMethod;
import io.d2a.laby.cfg.ListenerProvider;
import io.d2a.laby.cfg.annotations.listener.New;
import io.d2a.laby.cfg.annotations.listener.Old;
import io.d2a.laby.cfg.annotations.listener.SubscribeSettings;
import io.d2a.laby.cfg.annotations.listener.Var;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class ListenerController implements ListenerProvider {

  private final Map<String, List<ListenerMethod>> listeners;

  private final Map<Class<?>, Object> defaultValues = new HashMap<Class<?>, Object>() {{
    put(boolean.class, false);
    put(int.class, 0);
    put(long.class, 0L);
    put(float.class, 0F);
    put(double.class, 0D);
    put(char.class, '!');
    put(short.class, 0);
  }};

  ///

  public ListenerController() {
    this.listeners = Maps.newConcurrentMap();
  }

  // TODO: PLEASE consider a new name for this function. I am too lazy right now.
  private void a(final List<Object> parameters, final Parameter parameter, final Object obj) {
    if (obj == null && this.defaultValues.containsKey(parameter.getType())) {
      parameters.add(this.defaultValues.get(parameter.getType()));
    } else {
      parameters.add(obj);
    }
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
        if (parameter.isAnnotationPresent(New.class)) {
          this.a(parameters, parameter, newValue);
        } else if (parameter.isAnnotationPresent(Old.class)) {
          this.a(parameters, parameter, oldValue);
        } else if (parameter.isAnnotationPresent(Var.class)) {
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
  public void registerListeners(@Nonnull final Object... objects) {
    for (final Object obj : objects) {
      for (final Method method : obj.getClass().getDeclaredMethods()) {
        if (!method.isAnnotationPresent(SubscribeSettings.class)) {
          continue;
        }

        registerListener(
            obj,
            method.getAnnotation(SubscribeSettings.class),
            method
        );

        System.out.println("msgman :: registered method " + method.getName() +
            " for class " + obj.getClass().getSimpleName());
      }
    }
  }

  ///

  private void registerListener(
      @Nullable final Object instance,
      @Nonnull final SubscribeSettings subscriber,
      @Nonnull final Method method) {

    for (String var : subscriber.value()) {
      var = var.toLowerCase();

      if (!this.listeners.containsKey(var)) {
        this.listeners.put(var, Lists.newArrayList());
      }
      final List<ListenerMethod> m = this.listeners.get(var);

      m.add(new ListenerMethod(instance, subscriber, method));
      m.sort((o1, o2) -> { // sort by order-weight
        int w1 = o1.lann.priority().weight,
            w2 = o2.lann.priority().weight;
        return Integer.compare(w1, w2);
      });
    }
  }

}
