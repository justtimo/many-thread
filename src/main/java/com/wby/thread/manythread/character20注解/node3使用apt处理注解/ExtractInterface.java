//: annotations/ExtractInterface.java
// APT-based annotation processing.
package com.wby.thread.manythread.character20注解.node3使用apt处理注解;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 注解处理工具apt，这是Sun为了帮助注解的处理过程而提供的工具。由于这是该工具的第一版，其功能还比较基础，不过它确实有助于程序员的开发工作。
 *
 * 与javac一样，apt被设计为操作Java源文件，而不是编译后的类。默认情况下，apt会在处理完源文件后编译它们。如果在系统构建的过程中会'自动创建一些新的源文件，那么这个特性非常有用。
 * 事实上，apt会检查新生成的源文件中注解，然后将所有文件一同编译。
 *
 * 当注解处理器生成一个新的源文件时，该文件会在新一轮（round，Sun文档中这样称呼它）的注解处理中接受检查。该工具会一轮一轮地处理，直到不再有新的源文件产生为止。然后它再编译所有的源文件。
 *
 * 程序员自定义的每一个注解都需要自己的处理器，而apt工具能够很容易地将多个注解处理器组合在一起。有了它，程序员就可以指定多个要处理的类，这比程序员自己遍历所有的类文件简单多了。
 * 此外还可以添加监听器，并在一轮注解处理过程结束的时候收到通知信息。
 *
 * 在撰写本章的时候，apt还不是一个正式的Ant任务（参见http∶//MindView.net/Books/ BetterJava中的附件），不过显然可以将其作为—个Ant的外部任务运行。
 * 要想编译这一节中出现的注解处理器，你必须将tools.jar设置在你的classpath中，这个工具类库同时还包含了 com.sun.mirror.*接口。
 *
 * 通过使用AnnotationProcessorFactory，apt能够为每一个它发现的注解生成一个正确的注解 【1074处理器。当你使用apt的时候，必须指明一个工厂类，或者指明能找到apt所需的工厂类的路径
 * 否则，apt会踏上一个神秘的探索之旅，详细的信息可以在Sun文档的"开发一个注解处理器"一节中找到。
 *
 * 使用apt生成注解处理器时，我们无法利用Java的反射机制，因为我们操作的是源代码，而不是编译后的类9。使用mirror API能够解决这个问题，它使我们能够在未经编译的源代码中查看方法，域以及类型。
 *
 * 下面是一个自定义的注解，使用它可以把一个类中的public方法提取出来，构造成一个新的接口∶
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.SOURCE)
public @interface ExtractInterface {
  public String value();
} ///:~
/**
 * RetentionPolicy是SOURCE，因为当我们从一个使用了该注解的类中抽取出接口之后，没有必要再保留这些注解信息。下面的类有一个公共方法，我们将会把它抽取到一个有用接口中∶
 */
@ExtractInterface("IMultiplier")
class Multiplier {
  public int multiply(int x, int y) {
    int total = 0;
    for(int i = 0; i < x; i++)
      total = add(total, y);
    return total;
  }
  private int add(int x, int y) { return x + y; }
  public static void main(String[] args) {
    Multiplier m = new Multiplier();
    System.out.println("11*16 = " + m.multiply(11, 16));
  }
} /* Output:
11*16 = 176
*///:~
/**
 * 在Multiplier类中（它只对正整数起作用）有一个multiplyO方法，该方法多次调用一个私有的addO方法以实现乘法操作。addO方法不是公共的，因此不将其作为接口的一部分。
 * 注解给出了值IMultiplier，这就是将要生成的接口的名字∶
 */
/*class InterfaceExtractorProcessor
        implements AnnotationProcessor {
  private final AnnotationProcessorEnvironment env;
  private ArrayList<MethodDeclaration> interfaceMethods =
          new ArrayList<MethodDeclaration>();
  public InterfaceExtractorProcessor(
          AnnotationProcessorEnvironment env) { this.env = env; }
  public void process() {
    for(TypeDeclaration typeDecl :
            env.getSpecifiedTypeDeclarations()) {
      ExtractInterface annot =
              typeDecl.getAnnotation(ExtractInterface.class);
      if(annot == null)
        break;
      for(MethodDeclaration m : typeDecl.getMethods())
        if(m.getModifiers().contains(Modifier.PUBLIC) &&
                !(m.getModifiers().contains(Modifier.STATIC)))
          interfaceMethods.add(m);
      if(interfaceMethods.size() > 0) {
        try {
          PrintWriter writer =
                  env.getFiler().createSourceFile(annot.value());
          writer.println("package " +
                  typeDecl.getPackage().getQualifiedName() +";");
          writer.println("public interface " +
                  annot.value() + " {");
          for(MethodDeclaration m : interfaceMethods) {
            writer.print("  public ");
            writer.print(m.getReturnType() + " ");
            writer.print(m.getSimpleName() + " (");
            int i = 0;
            for(ParameterDeclaration parm :
                    m.getParameters()) {
              writer.print(parm.getType() + " " +
                      parm.getSimpleName());
              if(++i < m.getParameters().size())
                writer.print(", ");
            }
            writer.println(");");
          }
          writer.println("}");
          writer.close();
        } catch(IOException ioe) {
          throw new RuntimeException(ioe);
        }
      }
    }
  }
}*/ ///:~
/**
 * 所有的工作都在processO方法中完成。在分析一个类的时候，我们用MethodDeclaration类以及其上的getModifiersO方法来找到public方法（不包括static的那些）。
 * 一旦找到我们所需的public方法，就将其保存在一个ArrayList中，然后在一个java文件中，创建新的接口中的方法定义。
 *
 * 注意，处理器类的构造器以AnnotationProcessorEnvironment对象为参数。通过该对象，我们就能知道apt正在处理的所有类型（类定义），并且可以通过它获得Messager对象和Filer对象。
 * Messager对象可以用来向用户报告信息，比如处理过程中发生的任何错误，以及错误在源代码中出现的位置等。Filer是一种Print Writer，我们可以通过它创建新的文件。
 * 不使用普通的 PrintWriter而使用Filer对象的主要原因是，只有这样apt才能知道我们创建的新文件，从而对新文件进行注解处理，并且在需要的时候编译它们。
 *
 * 同时我们看到，Filer的createSourceFile（）方法以将要新建的类或接口的名字，打开了一个普通的输出流。现在还没有什么工具帮助程序员创建Java语言结构，
 * 所以我们只能用基本的 printO和println（O方法来生成Java源代码。因此，你必须小心仔细地处理括号，确保其闭合，并且确保生成的代码语法正确。
 *
 * apt工具需要一个工厂类来为其指明正确的处理器，然后它才能调用处理器上的process（）方法∶
 */
/*class InterfaceExtractorProcessorFactory
        implements AnnotationProcessorFactory {
  public AnnotationProcessor getProcessorFor(
          Set<AnnotationTypeDeclaration> atds,
          AnnotationProcessorEnvironment env) {
    return new InterfaceExtractorProcessor(env);
  }
  public Collection<String> supportedAnnotationTypes() {
    return
            Collections.singleton("annotations.ExtractInterface");
  }
  public Collection<String> supportedOptions() {
    return Collections.emptySet();
  }
} *////:~
/**
 * AnnotationProcessorFactory接口只有三个方法。如你所见，其中之一的getProcessorFor（）方法返回注解处理器，该方法以包含类型声明的Set（使用apt工具时传入的Java类）
 * 以及 AnnotationProcessorEnvironment对象为参数（将传入给处理器对象）。另外两个方法是 supportedAnnotationTypesO和supportedOptionsO，程序员可以通过它们检查一下，
 * 是否apt工具发现的所有的注解都有相应的处理器，是否所有控制台输入的参数都是你提供支持的选项。其中， supportedAnnotationTypes O）方法尤其重要，因为一旦在返回的String集合中没有你的注解的完整类名，
 * apt就会抱怨没有找到相应的处理器，从而发出警告信息，然后什么也不做就退出。
 *
 * 以上例子中的处理器与工厂类都在annotations包中，在InterfaceExtractorProcessor.java开头的注释文字中，我根据anotations的目录结构，在Exec标记处给出了需要从命令行输入的命令。
 * 它将告诉apt工具，使用上面的工厂类来处理Multiplierjava文件。参数-s说明任何新产生的文件都必须放在annotations目录中。通过处理器中的printlnO语句，估计你已经能猜到最终生成的 IMultiplierjava会是什么样子了;
 */
interface IMultjplier {
  public int multiply (int x,int y);
}
/**
 * apt也会编译这个新产生的文件，因此你将在相同的目录中看到IMultiplier.class文件。
 * 练习2∶（3）为抽取出来的接口添加对除法的支持。
 */
