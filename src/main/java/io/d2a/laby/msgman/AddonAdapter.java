package io.d2a.laby.msgman;

import java.util.List;
import net.labymod.api.LabyModAddon;
import net.labymod.settings.elements.SettingsElement;

public class AddonAdapter extends LabyModAddon {

  protected void catchException(final Exception exception) {
    System.out.println("msgman :: WARNING: Caught exception:");
    System.out.println(" --- ");
    exception.printStackTrace();
    System.out.println(" --- ");
  }

  @Override
  public void onEnable() {
    try {
      this.onEnableUnsafe();
    } catch (Exception exception) {
      catchException(exception);
    }
  }

  public void onEnableUnsafe() throws Exception {
  }

  @Override
  public void loadConfig() {
    try {
      this.loadConfigUnsafe();
    } catch (Exception exception) {
      catchException(exception);
    }
  }

  public void loadConfigUnsafe() throws Exception {

  }

  @Override
  protected void fillSettings(final List<SettingsElement> list) {

  }

}
