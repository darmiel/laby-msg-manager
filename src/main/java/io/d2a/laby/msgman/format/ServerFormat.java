package io.d2a.laby.msgman.format;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.d2a.laby.msgman.msg.Direction;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import net.labymod.utils.ServerData;

public class ServerFormat {

  private static final Gson gson = new GsonBuilder()
      .serializeNulls()
      .create();

  public final String name;
  public final List<String> address;
  public final List<Room> rooms;

  public ServerFormat(final String name, final List<String> address,
      final List<Room> rooms) {
    this.name = name;
    this.address = address;
    this.rooms = rooms;
  }

  @Override
  public String toString() {
    return "ServerFormat{" +
        "name='" + name + '\'' +
        ", address=" + address +
        ", rooms=" + rooms +
        '}';
  }

  public boolean accepts(final ServerData server) {
    return this.address.stream()
        .map(String::toLowerCase)
        .anyMatch(server.getIp()::equalsIgnoreCase);
  }

  ///

  public static ServerFormat parse (final File file) throws IOException {
    try (final FileReader reader = new FileReader(file)) {
      return gson.fromJson(reader, ServerFormat.class);
    }
  }

  public static Optional<ServerFormat> parseUnsafe (final File file) {
    try {
      return Optional.of(parse(file));
    } catch (Exception exception) {
      exception.printStackTrace();
    }
    return Optional.empty();
  }

  ///

  public static class Room {

    public final String type;
    public final Format[] format;

    public Room(final String type, final Format[] format) {
      this.type = type;
      this.format = format;
    }

    @Override
    public String toString() {
      return "Room{" +
          "type='" + type + '\'' +
          ", format=" + Arrays.toString(format) +
          '}';
    }

    public static class Format {

      public final Direction[] direction;
      public final Map<String, String> meta;
      public final List<List<String>> match;

      public Format(
          final Direction[] direction,
          final Map<String, String> meta,
          final List<List<String>> match
      ) {
        this.direction = direction;
        this.meta = meta;
        this.match = match;
      }

      @Override
      public String toString() {
        return "Format{" +
            "direction=" + Arrays.toString(direction) +
            ", meta=" + meta +
            ", match=" + match +
            '}';
      }
    }
  }

}
