package io.d2a.laby.msgman.svr;

public class Author {

  public enum AuthorType {
    PERSON,
    SERVER;
  }

  private final String name;
  private final AuthorType type;

  public Author(final String name, final AuthorType type) {
    this.name = name;
    this.type = type;
  }

  /// Getters

  public AuthorType getType() {
    return type;
  }

  public String getName() {
    return name;
  }

}
