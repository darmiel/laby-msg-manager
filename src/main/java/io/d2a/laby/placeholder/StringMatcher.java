package io.d2a.laby.placeholder;

import java.util.regex.Pattern;

public class StringMatcher {

  public static final Pattern GROUP_PATTERN = Pattern
      .compile("(?<![\\\\])(\\{[A-Za-z\\s+]+(?<![\\\\])})");

  private final String pattern;

  // Hello, my name is {Name} and I'm {Age} years old.
  // Hello, my name is Daniel and I'm 10000 years old.
  // Name = Daniel
  // Age = 10000
  public StringMatcher(final String pattern) {
    this.pattern = pattern;
  }
}