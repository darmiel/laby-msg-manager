package io.d2a.laby.cfg;

import io.d2a.laby.cfg.annotations.listener.ListenSettingsChange;
import java.lang.reflect.Method;

public class ListenerMethod {

    public final ListenSettingsChange lann;
    public final Method method;
    public final Object instance;

    public ListenerMethod(final Object instance, final ListenSettingsChange lann, final Method method) {
      this.instance = instance;
      this.lann = lann;
      this.method = method;
    }
  }