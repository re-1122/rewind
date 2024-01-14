package com.koyomiji.rewind.remapper;

import static org.objectweb.asm.Opcodes.ACC_STATIC;

import com.koyomiji.rewind.ReWind;
import java.util.Arrays;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Type;
import org.objectweb.asm.commons.ClassRemapper;

public class DirectClassRemapper extends ClassRemapper {
  Mapping mapping;

  public DirectClassRemapper(ClassVisitor cv, Mapping mapping) {
    super(cv, new DirectRemapper(mapping));
    this.mapping = mapping;
  }

  @Override
  public MethodVisitor visitMethod(int access, String methodName, String desc,
                                   String signature, String[] exceptions) {
    MethodVisitor mv =
        super.visitMethod(access, methodName, desc, signature, exceptions);

    boolean isStatic = (access & ACC_STATIC) != 0;
    MemberIdentifier methodIdentifier =
        new MemberIdentifier(className, methodName, desc);
    int lastParamIndex =
        TypeHelper.lastParamIndex(Type.getArgumentTypes(desc), isStatic);
    String[] paramNames = mapping.getParamNames(methodIdentifier);

    return new MethodVisitor(api, mv) {
      int paramIndex = 0;

      @Override
      public void visitLocalVariable(String name, String descriptor,
                                     String signature, Label start, Label end,
                                     int index) {
        if (index < lastParamIndex && !name.equals("this")) {
          name = paramNames[paramIndex];
          paramIndex++;
        }

        super.visitLocalVariable(name, descriptor, signature, start, end,
                                 index);
      }
    };
  }
}
