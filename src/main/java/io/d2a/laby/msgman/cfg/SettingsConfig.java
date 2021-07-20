package io.d2a.laby.msgman.cfg;

import io.d2a.laby.cfg.annotations.Settings;

public class SettingsConfig {

  @Settings("enabled")
  public boolean enabled = true;

  @Settings("name")
  public String name = "Daniel";

  @Settings
  public int schwul = 0;

}
