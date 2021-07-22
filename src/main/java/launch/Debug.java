package launch;

import com.google.gson.GsonBuilder;
import io.d2a.laby.placeholder.PlaceholderMapper;
import io.d2a.laby.placeholder.Test;

public class Debug {

  // \[Du\s\-\>{Target}\] {Message}
  public static void main(String[] args) {
    final Test test = PlaceholderMapper.mapUnsafe(Test.class, null, "Hallo")
        .orElseThrow(IllegalAccessError::new);

    System.out.println("Test: " + new GsonBuilder()
        .serializeNulls()
        .create()
        .toJson(test));
  }

}
