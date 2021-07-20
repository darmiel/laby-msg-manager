package io.d2a.laby.cfg.element;


import com.mojang.blaze3d.matrix.MatrixStack;
import net.labymod.main.LabyMod;
import net.labymod.utils.DrawUtils;

public class HeaderElement extends SettingsElementAdapter {

  private final DrawUtils draw;
  private int height;
  private int spaceTop;

  public HeaderElement(final String text, final int spaceTop, final int height) {
    super(text, null);
    this.height = height;
    this.spaceTop = spaceTop;

    this.draw = LabyMod.getInstance().getDrawUtils();
  }

  // Render

  @Override
  public void draw(
      final MatrixStack matrixStack,
      final int x,
      final int y,
      final int maxX,
      final int maxY,
      final int mouseX,
      final int mouseY
  ) {
    super.draw(matrixStack, x, y, maxX, maxY, mouseX, mouseY);

    final int absoluteY = y + this.spaceTop;
    this.draw.drawString(matrixStack, super.getDisplayName(), x, absoluteY);
  }

  @Override
  public int getEntryHeight() {
    return this.height;
  }

  //

  // Getter & Setter

  public int getHeight() {
    return height;
  }

  public void setHeight(final int height) {
    this.height = height;
  }

  public int getSpaceTop() {
    return spaceTop;
  }

  public void setSpaceTop(final int spaceTop) {
    this.spaceTop = spaceTop;
  }

  ///
}
