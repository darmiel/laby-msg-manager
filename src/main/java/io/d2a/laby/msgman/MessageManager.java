package io.d2a.laby.msgman;

import io.d2a.laby.cfg.annotations.listener.New;
import io.d2a.laby.cfg.annotations.listener.Old;
import io.d2a.laby.cfg.annotations.listener.SubscribeSettings;
import io.d2a.laby.cfg.ctl.ConfigController;
import io.d2a.laby.msgman.cfg.SettingsConfig;
import java.util.List;
import net.labymod.settings.elements.SettingsElement;

public class MessageManager extends LabyModAddonAdapter {

  private final SettingsConfig config = new SettingsConfig();
  private final ConfigController<SettingsConfig> cfgCtl = ConfigController
      .fromUnsafe(this, SettingsConfig.class, config)
      .orElseThrow(IllegalStateException::new)
      .registerAll(this);

  @Override
  public void onEnableUnsafe() throws Exception {
    System.out.println("It works! (or does it?)");
  }

  @Override
  public void loadConfigUnsafe() throws Exception {
    this.cfgCtl.readConfig();
  }

  @Override
  public void fillSettingsUnsafe(final List<SettingsElement> list) throws Exception {
    this.cfgCtl.getPageCtl().fillSettings(this.config, list);
    System.out.println("after fill: " + this.config.enabled + ", name: " + this.config.name);
  }

  @SubscribeSettings("enable")
  public void onEnableChange(@Old boolean o, @New boolean n) {
    System.out.println("msgman :: enable :: o: " + o + ", n: " + n);
  }

}
