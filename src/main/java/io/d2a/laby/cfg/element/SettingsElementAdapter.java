package io.d2a.laby.cfg.element;

import net.labymod.settings.elements.SettingsElement;

public class SettingsElementAdapter extends SettingsElement {

  public SettingsElementAdapter(final String displayName, final String description,
      final String configEntryName) {
    super(displayName, description, configEntryName);
  }

  public SettingsElementAdapter(final String displayName, final String configEntryName) {
    super(displayName, configEntryName);
  }

  @Override
  public void drawDescription(final int i, final int i1, final int i2) {

  }

  @Override
  public void mouseClicked(final int i, final int i1, final int i2) {

  }

  @Override
  public void mouseRelease(final int i, final int i1, final int i2) {

  }

  @Override
  public void mouseClickMove(final int i, final int i1, final int i2) {

  }

  @Override
  public void charTyped(final char c, final int i) {

  }

  @Override
  public void unfocus(final int i, final int i1, final int i2) {

  }

  @Override
  public int getEntryHeight() {
    return 0;
  }

}
