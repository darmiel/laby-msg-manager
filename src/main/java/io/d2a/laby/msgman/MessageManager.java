package io.d2a.laby.msgman;

import io.d2a.laby.cfg.ConfigController;
import io.d2a.laby.cfg.ListenerController;
import io.d2a.laby.cfg.annotations.listener.ListenSettingsChange;
import io.d2a.laby.cfg.annotations.listener.ListenSettingsChange.Order;
import io.d2a.laby.cfg.annotations.listener.NewVal;
import io.d2a.laby.cfg.annotations.listener.OldVal;
import io.d2a.laby.cfg.annotations.listener.VarName;
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

    // listeners
    final ListenerController<SettingsConfig> lstCtl = new ListenerController<>(this.cfgCtl);
    lstCtl.registerListener(this);

    System.out.println("msgman :: DEBUG :: Alert old: false, new: true ...");
    lstCtl.alert("enabled", false, true);
    System.out.println();
    System.out.println("msgman :: DEBUG :: Alert old: true, new: false ...");
    lstCtl.alert("enabled", true, false);
  }

  // EXAMPLES:

  @ListenSettingsChange(value = "enabled", priority = Order.NORMAL)
  public void onEnableChangeNormal(@OldVal boolean o, @NewVal boolean n, @VarName String var) {
    System.out.println("msgman :: NORM onEnableChange o: " + o + ", n: " + n + ", var: " + var);
  }

  @ListenSettingsChange(value = "enabled", priority = Order.NORMAL)
  public void onEnableChangeNormalON(@OldVal boolean o, @NewVal boolean n) {
    System.out.println("msgman :: NORM onEnableChangeON o: " + o + ", n: " + n);
  }

  @ListenSettingsChange(value = "enabled", priority = Order.NORMAL)
  public void onEnableChangeNormalNO(@NewVal boolean n, @OldVal boolean o) {
    System.out.println("msgman :: NORM onEnableChangeNO o: " + o + ", n: " + n);
  }

  @ListenSettingsChange(value = "enabled", priority = Order.LATE)
  public void onEnableChangeLateNO(@NewVal boolean n, @OldVal boolean o) {
    System.out.println("msgman :: LATE onEnableChangeLateNO o: " + o + ", n: " + n);
  }

  @ListenSettingsChange(value = "enabled", priority = Order.EARLY)
  public void onEnableChangeEarly(@OldVal boolean o, @NewVal boolean n, @VarName String var) {
    System.out.println("msgman :: EARLY onEnableChange o: " + o + ", n: " + n + ", var: " + var);
  }


}
