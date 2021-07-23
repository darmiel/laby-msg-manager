package io.d2a.laby.msgman.format.server;

import io.d2a.laby.msgman.msg.Direction;
import java.util.Map;

public class Format {

  private final Direction[] direction;
  private final Map<String, String> meta;
  private final String[][] match;

  public Format(
      final Direction[] direction,
      final Map<String, String> meta,
      final String[][] match
  ) {
    this.direction = direction;
    this.meta = meta;
    this.match = match;
  }

  public Direction[] getDirection() {
    return direction;
  }

  public Map<String, String> getMeta() {
    return meta;
  }

  public String[][] getMatch() {
    return match;
  }
}
