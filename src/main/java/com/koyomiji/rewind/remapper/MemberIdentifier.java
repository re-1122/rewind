package com.koyomiji.rewind.remapper;

import java.util.Objects;

public class MemberIdentifier {
  public final String owner;
  public final String name;
  public final String desc;

  public MemberIdentifier(String owner, String name, String desc) {
    this.owner = owner;
    this.name = name;
    this.desc = desc;
  }

  public MemberIdentifier(String owner, String name) {
    this.owner = owner;
    this.name = name;
    this.desc = null;
  }

  public boolean isField() { return desc == null; }

  public boolean isMethod() { return desc != null; }

  public static MemberIdentifier fromString(String str) {
    String owner = null;
    String name = null;
    String desc = null;
    int lastDot = str.lastIndexOf('.');

    if (lastDot != -1) {
      owner = str.substring(0, lastDot);
      str = str.substring(lastDot + 1);
    } else {
      throw new IllegalArgumentException("Invalid MemberIdentifier string: " +
                                         str);
    }

    int lastParen = str.lastIndexOf('(');
    if (lastParen != -1) {
      name = str.substring(0, lastParen);
      desc = str.substring(lastParen);
    } else {
      name = str;
    }
    return new MemberIdentifier(owner, name, desc);
  }

  @Override
  public String toString() {
    return (owner == null ? "" : owner + ".") + name +
        (desc == null ? "" : desc);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;
    MemberIdentifier that = (MemberIdentifier)o;
    return Objects.equals(owner, that.owner) &&
        Objects.equals(name, that.name) && Objects.equals(desc, that.desc);
  }

  @Override
  public int hashCode() {
    return Objects.hash(owner, name, desc);
  }
}
