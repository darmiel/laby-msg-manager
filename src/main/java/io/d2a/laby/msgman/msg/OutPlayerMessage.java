package io.d2a.laby.msgman.msg;

import net.labymod.main.LabyMod;

public class OutPlayerMessage extends PlayerMessage {

  public OutPlayerMessage(final String receiver, final String message) {
    super(Direction.OUTBOUND, null, receiver, message);
  }

  @Override
  public String getSender() {
    return LabyMod.getInstance().getPlayerName();
  }

}
