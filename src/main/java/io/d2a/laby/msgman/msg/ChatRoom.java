package io.d2a.laby.msgman.msg;

import com.google.common.collect.Lists;
import java.util.List;

public class ChatRoom {

  private final String name;
  private final boolean custom;
  private final List<PlayerMessage> messages;

  public ChatRoom(final String name, final boolean custom) {
    this.name = name;
    this.custom = custom;
    this.messages = Lists.newArrayList();
  }

  public String getName() {
    return name;
  }

  public boolean isCustom() {
    return custom;
  }

  public List<PlayerMessage> getMessages() {
    return messages;
  }

}
