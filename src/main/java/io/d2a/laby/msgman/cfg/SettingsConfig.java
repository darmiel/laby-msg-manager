package io.d2a.laby.msgman.cfg;

import io.d2a.laby.cfg.Dummy;
import io.d2a.laby.cfg.annotations.Header;
import io.d2a.laby.cfg.annotations.Settings;
import net.labymod.utils.Material;

public class SettingsConfig {

  /// Default Settings
  @Header("&aDefault Settings")
  private final Dummy dummy1 = Dummy.EMPTY;

  @Settings(value = "&bEnable", icon = Material.LEVER)
  public boolean enabled = true;
  /// ------------------------------------------------------------------------------------------

  /// Other
  @Header("&3Other")
  private final Dummy dummy2 = Dummy.EMPTY;

  @Settings(value = "&6&lNAME!", icon = Material.PAPER)
  public String name = "Daniel";
  /// ------------------------------------------------------------------------------------------

}
