//: net/mindview/atunit/AtUnitRemover.java
// Displays @Unit annotations in compiled class files. If
// first argument is "-r", @Unit annotations are removed.
// {Args: ..}
// {Requires: javassist.bytecode.ClassFile;
// You must install the Javassist library from
// http://sourceforge.net/projects/jboss/ }
package com.wby.thread.manythread.character20注解.node5基于注解的单元测试.child4移除测试代码;

import com.wby.thread.manythread.net.mindview.atunit.ClassNameFinder;
import com.wby.thread.manythread.net.mindview.util.BinaryFile;
import com.wby.thread.manythread.net.mindview.util.ProcessFiles;
import javassist.*;
import javassist.bytecode.*;
import javassist.bytecode.annotation.*;
import javassist.expr.*;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import static com.wby.thread.manythread.net.mindview.util.Print.print;

/**
 * 对许多项目而言，在发布的代码中是否保留测试代码并没什么区别（特别是在如果你将所有的测试方法都声明为private的情况下，如果你喜欢就可以这么做），但是在有的情况下，
 * 我们确实希望将测试代码清除掉，精简发布的程序，或者就是不希望测试代码暴露给客户。
 *
 * 与自己动手删除测试代码相比，这需要更复杂的字节码工程。不过开源的Javassist工具类库é将字节码工程带入了一个可行的领域。下面的程序接受一个-r标志作为其第一个参数;
 * 如果你提供了该标志，那么它就会删除所有的@Test注解，如果你没有提供该标记，那它则只会打印出@Test注解。这里同样使用ProcessFiles来遍历你选择的文件和目录∶
 */
public class AtUnitRemover
implements ProcessFiles.Strategy {
  private static boolean remove = false;
  public static void main(String[] args) throws Exception {
    if(args.length > 0 && args[0].equals("-r")) {
      remove = true;
      String[] nargs = new String[args.length - 1];
      System.arraycopy(args, 1, nargs, 0, nargs.length);
      args = nargs;
    }
    new ProcessFiles(
      new AtUnitRemover(), "class").start(args);
  }
  public void process(File cFile) {
    boolean modified = false;
    try {
      String cName = ClassNameFinder.thisClass(
        BinaryFile.read(cFile));
      if(!cName.contains("."))
        return; // Ignore unpackaged classes
      ClassPool cPool = ClassPool.getDefault();
      CtClass ctClass = cPool.get(cName);
      for(CtMethod method : ctClass.getDeclaredMethods()) {
        MethodInfo mi = method.getMethodInfo();
        AnnotationsAttribute attr = (AnnotationsAttribute)
          mi.getAttribute(AnnotationsAttribute.visibleTag);
        if(attr == null) continue;
        for(Annotation ann : attr.getAnnotations()) {
          if(ann.getTypeName()
             .startsWith("net.mindview.atunit")) {
            print(ctClass.getName() + " Method: "
              + mi.getName() + " " + ann);
            if(remove) {
              ctClass.removeMethod(method);
              modified = true;
            }
          }
        }
      }
      // Fields are not removed in this version (see text).
      if(modified)
        ctClass.toBytecode(new DataOutputStream(
          new FileOutputStream(cFile)));
      ctClass.detach();
    } catch(Exception e) {
      throw new RuntimeException(e);
    }
  }
} ///:~
/**
 * ClassPool是一种全景，它记录了你正在修改的系统中的所有的类，并能够保证所有类在修改后的一致性。你必须从ClassPool中取得每个CtClass，这与使用类加载器和Class.forNameO向 JVM加载类的方式类似。
 *
 * CtClass包含的是类对象的字节码，你可以通过它取得类有关的信息，并且操作类中的代码。在这里，我们调用getDeclaredMethodsO（与Java的反射机制一样），然后从每个CtMethod对象中取得一个MethodInfo对象。
 * 通过该对象，我们察看其中的注解信息。如果一个方法带有 net.mindview.atunit包中的注解，就将该方法删除掉。
 *
 * 如果类被修改过了，就用新的类覆盖原始的类文件。
 *
 * 在撰写本书时，Javassist刚刚加入了"删除"功能9，同时我们发现，删除@TestProperty域比删除方法复杂得多。因为，有些静态初始化的操作可能会引用这些域，所以你不能简单地将其删除。
 * 因此AtUnitRemover的当前版本只删除@Unit方法。不过，你应该查看一下Javassist网站的更新，因为删除域的功能以后可能也将实现。与此同时，对于AtUnitExternalText.java演示的外部测试方法，
 * 可以直接删除测试代码生成的类文件，从而到达删除所有测试的目的。
 */
