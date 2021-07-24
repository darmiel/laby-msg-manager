package launch;

import io.d2a.laby.msgman.format.ServerFormat;
import java.io.File;
import java.util.Arrays;

public class Debug {

  // \[Du\s\-\>{Target}\] {Message}
  public static void main(String[] args) {
    final File file = new File("servers", "horstblocks.json");
    final ServerFormat fmt = ServerFormat.parseUnsafe(file)
        .orElseThrow(IllegalAccessError::new);
    System.out.println("Format: " + fmt);
    System.out.println("Name:  "+ fmt.name + " with rooms: " + fmt.rooms);
  }

}
