package io.d2a.laby.placeholder;

import io.d2a.laby.placeholder.annotations.DefaultPattern;
import io.d2a.laby.placeholder.annotations.Placeholder;

public class Test {

  @DefaultPattern
  public static final String pattern =
      "Hello! My name is {Name+} and I'm {Age} years old.";

  @Placeholder("Name+")
  public String name;

  @Placeholder("Age")
  public String age;

}
