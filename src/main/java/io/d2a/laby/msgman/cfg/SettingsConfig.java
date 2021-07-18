package io.d2a.laby.msgman.cfg;

import io.d2a.laby.cfg.annotations.Default;
import io.d2a.laby.cfg.annotations.Setting;

public class SettingsConfig {

  @Default
  @Setting("enabled")
  public boolean enabled = true;

  @Default
  @Setting("name")
  public String name = "Daniel";

  @Default
  @Setting
  public int schwul = 0;

}
