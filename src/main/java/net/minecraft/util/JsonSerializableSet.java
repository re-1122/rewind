package net.minecraft.util;

import com.google.common.collect.ForwardingSet;
import com.google.gson.JsonElement;
import java.util.Set;

/*
 * This is a skeleton file representing 1.11.2's
 * net/minecraft/util/JsonSerializableSet. Actual implementation will be
 * obtained at runtime.
 */
public class JsonSerializableSet
    extends ForwardingSet<String> implements IJsonSerializable {
  private final Set<String> underlyingSet = null;

  public JsonSerializableSet() {}

  public void fromJson(JsonElement json) {}

  public JsonElement getSerializableElement() { return null; }

  protected Set<String> delegate() { return null; }
}
