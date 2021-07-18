package io.d2a.laby.cfg;

public class Lang {

  /**
   * <code>This is Enabled</code> => <code>ThisIsEnabled</code>
   *
   * @param inp Input String in Non-Pascal-Case
   * @return input String as Pascal-Case
   */
  public static String toPascalCase(String inp) {
    final StringBuilder builder = new StringBuilder();

    while (!inp.isEmpty() && isSpace(inp.charAt(0))) {
      inp = inp.substring(1);
    }
    while (!inp.isEmpty() && isSpace(inp.charAt(inp.length() - 1))) {
      inp = inp.substring(0, inp.length() - 1);
    }

    if (inp.isEmpty()) {
      return "";
    }

    final char[] chars = inp.toCharArray();
    for (int i = 0; i < chars.length; i++) {
      char c = chars[i];
      if (i == 0) {
        c = Character.toUpperCase(c);
      } else if (isSpace(c)) {// is space or _?
        c = Character.toUpperCase(chars[++i]);
      } else {
        c = Character.toLowerCase(c);
      }
      builder.append(c);
    }

    return builder.toString();
  }

  /**
   * Checks if the specified character is a space, or a space-like character
   *
   * @param c char
   * @return true if char is a space-like character
   */
  private static boolean isSpace(final char c) {
    return c == '_' || c == ' ' || c == '-';
  }

}
