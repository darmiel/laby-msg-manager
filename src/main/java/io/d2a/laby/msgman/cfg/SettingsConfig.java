package io.d2a.laby.msgman.cfg;

import io.d2a.laby.cfg.Dummy;
import io.d2a.laby.cfg.annotations.Header;
import io.d2a.laby.cfg.annotations.Settings;

public class SettingsConfig {

  /// Default Settings
  @Header("&aDefault Settings")
  private final Dummy dummy1 = null;

  @Settings(value = "&bEnable", listener = "enable")
  public boolean enabled = true;

  /// Other
  @Header("&3Other")
  private final Dummy dummy2 = null;

  @Settings(value = "&6&lNAME!", icon = "PAPER")
  public String name = "Daniel";

}
