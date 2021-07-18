package io.d2a.laby.cfg;

import com.google.gson.JsonObject;
import io.d2a.laby.cfg.exceptions.SettingParseException;

public interface JsonProvider<T> {

  void readConfig(
      final JsonObject json,
      final T obj,
      final Runnable saveConfigAction
  ) throws SettingParseException, IllegalAccessException, NoSuchFieldException;



}
