package io.d2a.laby.msgman;

import io.d2a.laby.cfg.ConfigController;
import io.d2a.laby.msgman.cfg.SettingsConfig;
import javax.annotation.Nonnull;

public class MessageManager extends AddonAdapter {

  final ConfigController<SettingsConfig> cfgCtl = ConfigController
      .fromUnsafe(this, SettingsConfig.class)
      .orElseThrow(IllegalStateException::new);

  @Override
  public void onEnableUnsafe() throws Exception {
    super.onEnableUnsafe();
    System.out.println("It works! (or does it?)");
  }

  @Override
  public void loadConfigUnsafe() throws Exception {
    this.cfgCtl.parse();

    final @Nonnull SettingsConfig cfg = this.cfgCtl.getObj();
    System.out.println("Loaded config: " + cfg.name + " | " + cfg.enabled);
  }
}
