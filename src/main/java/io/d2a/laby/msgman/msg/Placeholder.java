package io.d2a.laby.msgman.msg;

import com.google.common.collect.Maps;
import java.util.List;
import java.util.Map;

public class Placeholder {

  private static Map<String, String> parse(final List<String> matches, final String string) {
    // result
    String input = string;

    // internal variables
    Map<String, String> values = null;
    String ph;
    String value;

    for (int i = 0; i < matches.size(); i++) {
      final String match = matches.get(i);

      // Check if current "matcher" is a placeholder
      if (!"".equals(ph = getPlaceholder(match))) {
        final String expected = getNextExpected(matches, i);
        if ("".equals(expected)) { // last?
          if (i != matches.size() - 1) {
            break;
          }
          value = input;
        } else { // next expected
          final int index = input.indexOf(expected);
          if (index < 0) {
            break;
          }
          value = input.substring(0, index);
        }

        // Save placeholder's value to map
        if (values == null) {
          values = Maps.newHashMap();
        }
        values.put(ph, value);

        // remove placeholder's value from input string
        input = input.substring(value.length());
      } else {
        // No Placeholder
        if (!input.startsWith(match)) {
          break;
        }
        input = input.substring(match.length());
      }
    }

    // does not match
    if (!input.isEmpty()) {
      values = null;
    }

    return values;
  }

  ///
  
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

}
