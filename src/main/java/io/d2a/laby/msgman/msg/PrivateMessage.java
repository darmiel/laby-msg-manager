package io.d2a.laby.msgman.msg;

public class PrivateMessage extends PlayerMessage {

  private final String receiver;

  public PrivateMessage(
      final Direction direction,
      final String sender,
      final String receiver,
      final String message
  ) {
    super(direction, sender, message);

    this.receiver = receiver;
  }

  public String getReceiver() {
    return receiver;
  }

}
