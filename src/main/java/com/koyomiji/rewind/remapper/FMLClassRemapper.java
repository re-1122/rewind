package com.koyomiji.rewind.remapper;

import com.google.common.collect.ImmutableMap;
import com.koyomiji.rewind.ReWind;
import java.util.Map;
import net.minecraftforge.fml.common.asm.transformers.deobf.FMLDeobfuscatingRemapper;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.commons.ClassRemapper;

public class FMLClassRemapper extends ClassRemapper {
  public FMLClassRemapper(ClassVisitor cv) { super(cv, new FMLRemapper()); }
}
