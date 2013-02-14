/*
 * Beangle, Agile Java/Scala Development Scaffold and Toolkit
 *
 * Copyright (c) 2005-2013, Beangle Software.
 *
 * Beangle is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Beangle is distributed in the hope that it will be useful.
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Beangle.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.beangle.commons.lang.asm;

import static org.objectweb.asm.Opcodes.*;

import java.util.List;
import java.util.Map;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.lang.reflect.ClassInfo;
import org.beangle.commons.lang.reflect.MethodInfo;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

/**
 * Class invocation proxy,delegate method invocation and property accessment.
 * It employ asm framework,dynamiclly generate a access class.
 * <p>
 * Usage:
 * 
 * <pre>
 * Mirror mirror = Mirror.get(YourBean.class);
 * // invoke any method
 * mirror.invoke(bean, &quot;somemethod&quot;, arg1, arg2);
 * </pre>
 * 
 * @author chaostone
 * @since 3.2.0
 */
public abstract class Mirror {

  private static Map<Class<?>, Mirror> proxies = CollectUtils.newHashMap();

  ClassInfo classInfo;

  /**
   * Delegate invocation to object's method with arguments.
   * 
   * @see #getIndex(String, Object...)
   */
  abstract public Object invoke(Object object, int methodIndex, Object... args);

  /**
   * Return method index.
   * index is 0 based,if not found ,return -1.
   * 
   * @see #invoke(Object, int, Object...)
   */
  public final int getIndex(String name, Object... args) {
    return classInfo.getIndex(name, args);
  }

  /**
   * invoke the method with the specified name and arguments.
   * <p>
   * It lookup method index by name and arguments,find first method matchs given sigature. The best
   * approach in many time invocations is get the index first and pass through to invoke.
   * <p>
   * In 100 000 000's benchmark test, this method is 35% slower than
   * invoke(obj,getIndex(method),args) form.the former consume 2800ms and the later using just
   * 1800ms. STRANGE!!!
   * 
   * @see #invoke(Object, int, Object...)
   */
  public final Object invoke(Object object, String method, Object... args) {
    return invoke(object, classInfo.getIndex(method, args), args);
  }

  /**
   * Get Mirror of given type.
   * <p>
   * First,it search from proxies cache,if not found, then generate new proxy class using asm.
   */
  public static final Mirror get(Class<?> type) {
    Mirror proxy = proxies.get(type);
    if (null != proxy) return proxy;

    synchronized (proxies) {
      proxy = proxies.get(type);
      if (null != proxy) return proxy;

      ClassInfo classInfo = ClassInfo.get(type);
      String className = type.getName();
      String accessClassName = className + "Mirror";
      if (accessClassName.startsWith("java.")) accessClassName = "beangle." + accessClassName;
      Class<?> accessClass = null;

      MirrorClassLoader loader = MirrorClassLoader.get(type);
      if (null == loader) return Mirrors.none();

      try {
        accessClass = loader.loadClass(accessClassName);
      } catch (ClassNotFoundException ignored) {
        String accessClassNameInternal = accessClassName.replace('.', '/');
        String classNameInternal = className.replace('.', '/');

        ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS);
        MethodVisitor mv;
        cw.visit(V1_1, ACC_PUBLIC + ACC_SUPER, accessClassNameInternal, null,
            "org/beangle/commons/lang/asm/Mirror", null);
        {
          mv = cw.visitMethod(ACC_PUBLIC, "<init>", "()V", null, null);
          mv.visitCode();
          mv.visitVarInsn(ALOAD, 0);
          mv.visitMethodInsn(INVOKESPECIAL, "org/beangle/commons/lang/asm/Mirror", "<init>", "()V");
          mv.visitInsn(RETURN);
          mv.visitMaxs(0, 0);
          mv.visitEnd();
        }
        // invoke method
        {
          mv = cw.visitMethod(ACC_PUBLIC + ACC_FINAL + ACC_VARARGS, "invoke",
              "(Ljava/lang/Object;I[Ljava/lang/Object;)Ljava/lang/Object;", null, null);
          mv.visitCode();

          List<MethodInfo> methods = classInfo.getMethods();
          if (methods.size() > 0) {
            mv.visitVarInsn(ALOAD, 1);
            mv.visitTypeInsn(CHECKCAST, classNameInternal);
            mv.visitVarInsn(ASTORE, 4);

            mv.visitVarInsn(ILOAD, 2);
            Label[] labels = new Label[methods.size()];
            for (int i = 0, n = labels.length; i < n; i++)
              labels[i] = new Label();
            Label defaultLabel = new Label();
            mv.visitTableSwitchInsn(0, labels.length - 1, defaultLabel, labels);

            StringBuilder buffer = new StringBuilder(128);
            for (int i = 0, n = labels.length; i < n; i++) {
              mv.visitLabel(labels[i]);
              if (i == 0) mv.visitFrame(Opcodes.F_APPEND, 1, new Object[] { classNameInternal }, 0, null);
              else mv.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
              mv.visitVarInsn(ALOAD, 4);

              buffer.setLength(0);
              buffer.append('(');

              MethodInfo info = methods.get(i);

              // In runtime generic method using rawtype to invoke.
              Class<?>[] paramTypes = info.method.getParameterTypes();
              for (int paramIndex = 0; paramIndex < paramTypes.length; paramIndex++) {
                mv.visitVarInsn(ALOAD, 3);
                mv.visitIntInsn(BIPUSH, paramIndex);
                mv.visitInsn(AALOAD);
                Type paramType = Type.getType(paramTypes[paramIndex]);
                switch (paramType.getSort()) {
                case Type.BOOLEAN:
                  mv.visitTypeInsn(CHECKCAST, "java/lang/Boolean");
                  mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/Boolean", "booleanValue", "()Z");
                  break;
                case Type.BYTE:
                  mv.visitTypeInsn(CHECKCAST, "java/lang/Byte");
                  mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/Byte", "byteValue", "()B");
                  break;
                case Type.CHAR:
                  mv.visitTypeInsn(CHECKCAST, "java/lang/Character");
                  mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/Character", "charValue", "()C");
                  break;
                case Type.SHORT:
                  mv.visitTypeInsn(CHECKCAST, "java/lang/Short");
                  mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/Short", "shortValue", "()S");
                  break;
                case Type.INT:
                  mv.visitTypeInsn(CHECKCAST, "java/lang/Integer");
                  mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/Integer", "intValue", "()I");
                  break;
                case Type.FLOAT:
                  mv.visitTypeInsn(CHECKCAST, "java/lang/Float");
                  mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/Float", "floatValue", "()F");
                  break;
                case Type.LONG:
                  mv.visitTypeInsn(CHECKCAST, "java/lang/Long");
                  mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/Long", "longValue", "()J");
                  break;
                case Type.DOUBLE:
                  mv.visitTypeInsn(CHECKCAST, "java/lang/Double");
                  mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/Double", "doubleValue", "()D");
                  break;
                case Type.ARRAY:
                  mv.visitTypeInsn(CHECKCAST, paramType.getDescriptor());
                  break;
                case Type.OBJECT:
                  mv.visitTypeInsn(CHECKCAST, paramType.getInternalName());
                  break;
                }
                buffer.append(paramType.getDescriptor());
              }

              buffer.append(')');
              buffer.append(Type.getDescriptor(info.method.getReturnType()));
              mv.visitMethodInsn(INVOKEVIRTUAL, classNameInternal, info.method.getName(), buffer.toString());

              switch (Type.getType(info.method.getReturnType()).getSort()) {
              case Type.VOID:
                mv.visitInsn(ACONST_NULL);
                break;
              case Type.BOOLEAN:
                mv.visitMethodInsn(INVOKESTATIC, "java/lang/Boolean", "valueOf", "(Z)Ljava/lang/Boolean;");
                break;
              case Type.BYTE:
                mv.visitMethodInsn(INVOKESTATIC, "java/lang/Byte", "valueOf", "(B)Ljava/lang/Byte;");
                break;
              case Type.CHAR:
                mv.visitMethodInsn(INVOKESTATIC, "java/lang/Character", "valueOf", "(C)Ljava/lang/Character;");
                break;
              case Type.SHORT:
                mv.visitMethodInsn(INVOKESTATIC, "java/lang/Short", "valueOf", "(S)Ljava/lang/Short;");
                break;
              case Type.INT:
                mv.visitMethodInsn(INVOKESTATIC, "java/lang/Integer", "valueOf", "(I)Ljava/lang/Integer;");
                break;
              case Type.FLOAT:
                mv.visitMethodInsn(INVOKESTATIC, "java/lang/Float", "valueOf", "(F)Ljava/lang/Float;");
                break;
              case Type.LONG:
                mv.visitMethodInsn(INVOKESTATIC, "java/lang/Long", "valueOf", "(J)Ljava/lang/Long;");
                break;
              case Type.DOUBLE:
                mv.visitMethodInsn(INVOKESTATIC, "java/lang/Double", "valueOf", "(D)Ljava/lang/Double;");
                break;
              }

              mv.visitInsn(ARETURN);
            }

            mv.visitLabel(defaultLabel);
            mv.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
          }
          mv.visitTypeInsn(NEW, "java/lang/IllegalArgumentException");
          mv.visitInsn(DUP);
          mv.visitTypeInsn(NEW, "java/lang/StringBuilder");
          mv.visitInsn(DUP);
          mv.visitLdcInsn("Method not found: ");
          mv.visitMethodInsn(INVOKESPECIAL, "java/lang/StringBuilder", "<init>", "(Ljava/lang/String;)V");
          mv.visitVarInsn(ILOAD, 2);
          mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append",
              "(I)Ljava/lang/StringBuilder;");
          mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "toString", "()Ljava/lang/String;");
          mv.visitMethodInsn(INVOKESPECIAL, "java/lang/IllegalArgumentException", "<init>",
              "(Ljava/lang/String;)V");
          mv.visitInsn(ATHROW);
          mv.visitMaxs(0, 0);
          mv.visitEnd();
        }
        cw.visitEnd();
        byte[] data = cw.toByteArray();
         try {
         (new java.io.FileOutputStream("/tmp/" + accessClassName + ".class")).write(data);
         } catch (Exception e) {
         }
        accessClass = loader.defineClass(accessClassName, data);
      }

      try {
        proxy = (Mirror) accessClass.newInstance();
        proxy.classInfo = classInfo;
        proxies.put(type, proxy);
        return proxy;
      } catch (Exception ex) {
        throw new RuntimeException("Error constructing method access class: " + accessClassName, ex);
      }
    }
  }
}
