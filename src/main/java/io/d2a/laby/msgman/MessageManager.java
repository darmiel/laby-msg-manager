package io.d2a.laby.msgman;

import io.d2a.laby.cfg.annotations.listener.New;
import io.d2a.laby.cfg.annotations.listener.Old;
import io.d2a.laby.cfg.annotations.listener.SubscribeSettings;
import io.d2a.laby.cfg.annotations.listener.Var;
import io.d2a.laby.cfg.ctl.ConfigController;
import io.d2a.laby.msgman.cfg.SettingsConfig;
import java.util.List;
import net.labymod.settings.elements.SettingsElement;

public class MessageManager extends AddonAdapter {

  final ConfigController<SettingsConfig> cfgCtl = ConfigController
      .fromUnsafe(this, SettingsConfig.class)
      .orElseThrow(IllegalStateException::new)
      .registerAll(this);

  @Override
  public void onEnableUnsafe() throws Exception {
    System.out.println("It works! (or does it?)");
  }

  @Override
  public void loadConfigUnsafe() throws Exception {
  }

  @Override
  public void fillSettingsUnsafe(final List<SettingsElement> list) throws Exception {
    cfgCtl.getPageCtl().fillSettings(this.cfgCtl.getObj(), list);
  }

}
