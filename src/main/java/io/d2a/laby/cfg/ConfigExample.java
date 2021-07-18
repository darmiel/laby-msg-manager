package io.d2a.laby.cfg;

import io.d2a.laby.cfg.annotations.Default;
import io.d2a.laby.cfg.annotations.Setting;
import io.d2a.laby.cfg.annotations.Sort;
import io.d2a.laby.cfg.annotations.SubscribeSetting;

public class ConfigExample {

  public class Config {

    @Default
    @Setting(value = "Enabled", icon = "ACACIA_FENCE")
    @Sort("Test")
    private boolean enabled = true;
  }

  public class Subscriber {

    @SubscribeSetting("Enabled")
    public void onEnabledChange(final boolean oldValue, final boolean newValue) {

    }
  }

}
