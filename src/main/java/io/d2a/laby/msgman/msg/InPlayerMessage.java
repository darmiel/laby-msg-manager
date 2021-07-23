package io.d2a.laby.msgman.msg;

import net.labymod.main.LabyMod;

public class InPlayerMessage extends PlayerMessage {

  public InPlayerMessage(final String sender,final String message) {
    super(Direction.INBOUND, sender, null, message);
  }

  @Override
  public String getReceiver() {
    return LabyMod.getInstance().getPlayerName();
  }

}
