package launch;

import io.d2a.laby.cfg.Lang;

public class Debug {

  public static void main(String[] args) {
    final String[] inp = new String[]{
        "Enabled",
        "Hello World",
        "hello_world",
        "my name is daniel",
        "this_is_a_bug_",
        "this_shouldnt_be_either____",
        "" // empty
    };
    for (final String s : inp) {
      System.out.println("orig: " + s);
      System.out.println("pasc: " + Lang.toPascalCase(s));
      System.out.println();
    }
  }
}
