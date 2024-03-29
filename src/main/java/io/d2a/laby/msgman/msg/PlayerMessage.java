package io.d2a.laby.msgman.msg;

public abstract class PlayerMessage {

  private final Direction direction;
  private final String sender;
  private final String receiver;
  private final String message;

  public PlayerMessage(
      final Direction direction,
      final String sender,
      final String receiver,
      final String message
  ) {
    this.direction = direction;
    this.sender = sender;
    this.receiver = receiver;
    this.message = message;
  }

  public Direction getDirection() {
    return direction;
  }

  public String getSender() {
    return sender;
  }

  public String getMessage() {
    return message;
  }

  public String getReceiver() {
    return receiver;
  }
}
