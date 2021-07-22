package io.d2a.laby.placeholder;

import javax.annotation.Nullable;

public interface Placeholable {

  @Nullable
  default String defaultPattern() {
    return null;
  }

}
