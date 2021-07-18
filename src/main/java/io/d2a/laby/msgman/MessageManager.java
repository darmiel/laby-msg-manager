package io.d2a.laby.msgman;

import io.d2a.laby.cfg.ConfigController;
import io.d2a.laby.cfg.annotations.listener.ListenSettingsChange;
import io.d2a.laby.cfg.annotations.listener.ListenSettingsChange.Order;
import io.d2a.laby.cfg.annotations.listener.NewVal;
import io.d2a.laby.cfg.annotations.listener.OldVal;
import io.d2a.laby.cfg.annotations.listener.VarName;
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

  @ListenSettingsChange(value = "enabled", priority = Order.NORMAL)
  public void onEnableChange(@OldVal boolean o, @NewVal boolean n, @VarName String var) {
    System.out.println("msgman :: onEnableChange (o = " + o + ", n = " + n + ", var = " + var + ")");
  }

  @ListenSettingsChange("name")
  public void onNameChange(@NewVal String n) {
    System.out.println("msgman :: onNameChange [name changed to:] (n = " + n + ")");
  }

}
