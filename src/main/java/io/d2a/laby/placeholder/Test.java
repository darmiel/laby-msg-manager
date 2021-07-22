package io.d2a.laby.placeholder;

import io.d2a.laby.placeholder.annotations.Placeholder;

public class Test implements Placeholable {

  @Override
  public String defaultPattern() {
    return "Hello! My name is {Name} and I'm {Age} years old.";
  }

  @Placeholder(
      value = "Name",
      maxWords = 2
  )
  public String name;

  @Placeholder("Age")
  public String age;

}
