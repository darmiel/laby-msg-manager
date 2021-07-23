package io.d2a.laby.msgman.format.server;

public class Server {

  private final String name;
  private final Room[] rooms;

  public Server(final String name, final Room[] rooms) {
    this.name = name;
    this.rooms = rooms;
  }

  public String getName() {
    return name;
  }

  public Room[] getRooms() {
    return rooms;
  }

}
