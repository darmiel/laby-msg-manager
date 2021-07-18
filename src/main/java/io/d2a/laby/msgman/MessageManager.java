package io.d2a.laby.msgman;

import io.d2a.laby.cfg.ConfigController;
import io.d2a.laby.cfg.annotations.listener.SubscribeSettings;
import io.d2a.laby.cfg.annotations.listener.SubscribeSettings.Order;
import io.d2a.laby.cfg.annotations.listener.New;
import io.d2a.laby.cfg.annotations.listener.Old;
import io.d2a.laby.cfg.annotations.listener.Var;
import io.d2a.laby.msgman.cfg.SettingsConfig;

public class MessageManager extends AddonAdapter {

  final ConfigController<SettingsConfig> cfgCtl = ConfigController
      .fromUnsafe(this, SettingsConfig.class)
      .orElseThrow(IllegalStateException::new)
      .registerAll(this);

  @Override
  public void onEnableUnsafe() throws Exception {
    super.onEnableUnsafe();
    System.out.println("It works! (or does it?)");
  }

  @Override
  public void loadConfigUnsafe() throws Exception {
    this.cfgCtl.parse();

    this.cfgCtl.alert("enabled", false, true);
    this.cfgCtl.alert("name", "Daniel", "Luis");
  }

  // EXAMPLES:

  @SubscribeSettings(value = "enabled", priority = Order.NORMAL)
  public void onEnableChange(@Old boolean o, @New boolean n, @Var String var) {
    System.out.println("msgman :: onEnableChange (o = " + o + ", n = " + n + ", var = " + var + ")");
  }

  @SubscribeSettings("name")
  public void onNameChange(@New String n) {
    System.out.println("msgman :: onNameChange [name changed to:] (n = " + n + ")");
  }

}
