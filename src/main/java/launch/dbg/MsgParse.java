package launch.dbg;

import java.util.Arrays;
import java.util.List;

public class MsgParse {

  private static String getPlaceholder(final String input) {
    if (input.startsWith("{{") && input.endsWith("}}")) {
      return input.substring(2, input.length() - 2).trim();
    }
    return "";
  }

  private static String getNextExpected(final List<String> matches, final int i) {
    final StringBuilder builder = new StringBuilder();
    for (int j = i + 1; j < matches.size(); j++) {
      final String m = matches.get(j);
      if (!"".equals(getPlaceholder(m))) {
        break;
      }
      builder.append(m);
    }
    return builder.toString();
  }

  ///////

  public static void main(String[] args) {
    // WARNING:
    // It is NOT supported that two placeholders are placed in a row
    final List<String> matches = Arrays.asList(
        "[",
        "{{ Player }}",
        " -> Du] ",
        "{{ Message }}"
    );

    String input = "[Mark -> Du] Hallo! Wie geht's?";
    boolean invalid = false;

    for (int i = 0; i < matches.size(); i++) {
      final String match = matches.get(i);

      String ph;
      if (!"".equals(ph = getPlaceholder(match))) {
        final String expected = getNextExpected(matches, i);
        System.out.println("Placeholder " + ph + " is expected to end at " + expected);
        String value;

        // to end
        if ("".equals(expected)) {
          value = input;
        } else {
          final int index = input.indexOf(expected);
          if (index < 0) {
            invalid = true;
            break;
          }
          value = input.substring(0, index);
        }

        System.out.println("Placeholder " + ph + " :: " + value);
        input = input.substring(value.length());

      } else {
        // No Placeholder
        if (input.startsWith(match)) {
          input = input.substring(match.length());
        } else {
          invalid = true;
          break;
        }
      }

    }

    System.out.println(">> New Input: " + input);
    System.out.println(">> Invalid: " + invalid);
  }

}
