package com.koyomiji.rewind.remapper;

import com.google.common.collect.ImmutableMap;
import java.util.Map;
import net.minecraftforge.fml.common.asm.transformers.deobf.FMLDeobfuscatingRemapper;
import org.objectweb.asm.commons.Remapper;

public class FMLRemapper extends Remapper {
  FMLDeobfuscatingRemapper fmlRemapper = FMLDeobfuscatingRemapper.INSTANCE;

  private static final Map<MemberIdentifier, MemberIdentifier>
      methodMapExcepts = ImmutableMap.of(
          new MemberIdentifier("net/minecraft/stats/Achievement",
                               "func_75966_h",
                               "()Lnet/minecraft/stats/Achievement;"),
          new MemberIdentifier("net/minecraft/stats/Achievement",
                               "func_75966_h",
                               "()Lnet/minecraft/stats/StatBase;"),
          new MemberIdentifier("net/minecraft/stats/Achievement",
                               "func_75971_g",
                               "()Lnet/minecraft/stats/Achievement;"),
          new MemberIdentifier("net/minecraft/stats/Achievement",
                               "func_75971_g",
                               "()Lnet/minecraft/stats/StatBase;"));

  @Override
  public String map(String typeName) {
    return fmlRemapper.map(typeName);
  }

  @Override
  public String mapFieldName(String owner, String name, String desc) {
    return fmlRemapper.mapFieldName(owner, name, desc);
  }

  @Override
  public String mapMethodName(String owner, String name, String desc) {
    if (methodMapExcepts.containsKey(new MemberIdentifier(owner, name, desc))) {
      MemberIdentifier newId =
          methodMapExcepts.get(new MemberIdentifier(owner, name, desc));
      owner = newId.owner;
      name = newId.name;
      desc = newId.desc;
    }

    return fmlRemapper.mapMethodName(owner, name, desc);
  }
}
