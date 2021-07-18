package io.d2a.laby.msgman;

import io.d2a.laby.cfg.Reader;
import io.d2a.laby.msgman.cfg.SettingsConfig;

public class MessageManager extends AddonAdapter {

  @Override
  public void onEnableUnsafe() throws Exception {
    super.onEnableUnsafe();
    System.out.println("It works! (or does it?)");
  }

  @Override
  public void loadConfigUnsafe() throws Exception {
    System.out.println("msgman :: config start");
    final SettingsConfig config = new SettingsConfig();
    final Reader<SettingsConfig> reader = new Reader<>(this, SettingsConfig.class, config);
    reader.parse();
    System.out.println("msgman :: config end");
  }
}
