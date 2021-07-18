package io.d2a.laby.cfg;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public interface ListenerProvider {

  void alert(
      @Nonnull String var,
      @Nullable final Object oldValue,
      @Nullable final Object newValue
  ) throws Exception;

  void registerListeners (
      @Nonnull final Object ... obj
  );

}
