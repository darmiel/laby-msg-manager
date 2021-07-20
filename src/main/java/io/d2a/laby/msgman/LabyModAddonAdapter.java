package io.d2a.laby.msgman;

import java.util.List;
import net.labymod.api.LabyModAddon;
import net.labymod.settings.elements.SettingsElement;

public class LabyModAddonAdapter extends LabyModAddon {

  ///

  public void onEnableUnsafe() throws Exception {

  }

  public void loadConfigUnsafe() throws Exception {

  }

  public void fillSettingsUnsafe(final List<SettingsElement> list) throws Exception {

  }

  ///

  @Override
  public void onEnable() {
    try {
      this.onEnableUnsafe();
    } catch (Exception exception) {
      catchException(exception);
    }
  }


  @Override
  public void loadConfig() {
    try {
      this.loadConfigUnsafe();
    } catch (Exception exception) {
      catchException(exception);
    }
  }

  @Override
  protected void fillSettings(final List<SettingsElement> list) {
    try {
      this.fillSettingsUnsafe(list);
    } catch (Exception exception) {
      catchException(exception);
    }
  }

  ///

  protected void catchException(final Exception exception) {
    System.out.println("msgman :: WARNING: Caught exception:");
    System.out.println(" --- ");
    exception.printStackTrace();
    System.out.println(" --- ");
  }

}
