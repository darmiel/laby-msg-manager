package launch;

import io.d2a.laby.msgman.format.ServerFormat;
import io.d2a.laby.msgman.msg.Placeholder;
import java.io.File;
import java.util.Arrays;
import java.util.List;

public class Debug {

  // \[Du\s\-\>{Target}\] {Message}
  public static void main(String[] args) {
    final List<String> matches = Arrays.asList("[",
        "{{ Sender }}",
        " -> ",
        "Du",
        "] ",
        "{{ Message }}");
    final String input = "[Schuechterner -> Du] e";
    System.out.println(Placeholder.parse(matches, input));
  }

}
