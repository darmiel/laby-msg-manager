package io.d2a.laby.msgman.format.server;

public class Room {

  private final String type;
  private final Format[] format;

  public Room(final String type, final Format[] format) {
    this.type = type;
    this.format = format;
  }

  public String getType() {
    return type;
  }

  public Format[] getFormat() {
    return format;
  }

}
