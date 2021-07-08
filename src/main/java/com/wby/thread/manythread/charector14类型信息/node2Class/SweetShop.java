package com.wby.thread.manythread.charector14类型信息.node2Class;//: typeinfo/SweetShop.java
// Examination of the way the class loader works.
import static com.wby.thread.manythread.net.mindview.util.Print.print;

/**
 * @Description:  要理解RTTI的原理，首先要知道类型信息在运行时是如何表示的。
 *  这个工作由 Class对象完成，它包含了与类相关的信息。实际上，Class对象就是用来创建类的所有的“常规”对象的。
 *  java使用Class对象来执行其RTTI，即使正在执行的是类似类型转换的操作。Class还拥有大量的使用RTTI的其他方式。
 *
 *  类是程序的一部分，每个类都有一个Class对象，即：编写并编译一个新类，就会产生一个Class对象(是被保存在一个同名的.class文件)。为了生成这个类的对象，
 *  JVM(java虚拟机)将会使用被称为“类加载器”的子系统
 *
 *  类加载器子系统实际上可以包含一条类加载器链，但是只有一个 原生类加载器 ，他是JVM实现的一部分。
 *  原生类加载器 加载的是所谓的 可信类 ，包括JAVA API类，他们通常是从本地硬盘加载的。
 *  这条链中，通常不需要添加额外的类加载器，但如果有特殊要求(以某种特殊的方式加载类，以支持web服务器应用或者在网络中下载类)，那么你有一种方式可以挂接额外的类加载器
 *
 *  所有的类是在对其第一次使用时动态加载到JVM中的。
 *  当程序创建第一个对类的静态成员的引用时，就会加载这个类。这证明了构造器也是类的静态方法，即使构造器前面没有使用static关键字。
 *  因此，使用new操作符创建类的新对象也会被当做对类的静态成员的引用
 *
 *  java程序在他开始运行之前并非完全加载，其各个部分是在必需时才加载的。动态加载的行为，在注入C++等静态语言中是很难或者不可能复制的
 *
 *  类加载器首先检查这个类的Class对象是否已经加载。如果没有加载，则默认的类加载器就会根据类名查找.class文件(比如某个附加类加载器可能会在数据库中查找字节码)
 *  在这个类的字节码被加载时，他们会被验证，以确保其没有被破坏，并没有包含不良代码
 *
 *  一旦某个类的Class对象被载入内存，他就被用来创建这个类的所有对象，见下例：
 */
class Candy {
  static { print("Loading Candy"); }
}

class Gum {
  static { print("Loading Gum"); }
}

class Cookie {
  static { print("Loading Cookie"); }
}

public class SweetShop {
  public static void main(String[] args) {
    print("inside main");
    new Candy();
    print("After creating Candy");
    try {
      Class<?> aClass = Class.forName("com.wby.thread.manythread.charector14类型信息.node2Class.Gum");
      System.out.println("---------------");
      System.out.println(aClass.getName());
      System.out.println(aClass.getSimpleName());
      System.out.println(aClass.getCanonicalName());
      System.out.println(aClass.getClassLoader());
      System.out.println(aClass.getDeclaredFields());
      System.out.println(aClass.getPackage());
      System.out.println(aClass.getTypeName());
      System.out.println("---------------");
    } catch(ClassNotFoundException e) {
      print("Couldn't find Gum");
    }
    print("After Class.forName(\"Gum\")");
    new Cookie();
    print("After creating Cookie");
  }
} /* Output:
inside main
Loading Candy
After creating Candy
Loading Gum
After Class.forName("Gum")
Loading Cookie
After creating Cookie
*///:~
/**
* @Description: 每个类Candy Gum Cookie都有一个static子句，该子句在类第一次被加载时执行。这时会打印语句告诉我们这个类什么时候被加载了。
 * 从输出可以看到，Class对象仅在需要的时候才被加载，static初始化是在类加载时进行的。
 *
 * 注意：Class.forName方法是Class类(所有Class对象都属于这个类)的一个static成员。
 *    class对象和其他对象一样，可以获取并操作他的引用(这也是类加载器的工作)。
 *    forName方法是取得Class对象引用的一种方法。以目标类文本名(注意大小写,必须是全限定名)的String作为入参，返回一个Class对象的引用。
 *
 * 对forName()的调用是为了他产生的副作用：如果Gum类没有被加载就加载他。加载过程中，Gum的static子句被执行
 * 前面的例子中，如果Class.forName()找不到要加载的类，则会抛出异常
 *
 * 无论何时，只要你想在运行时使用类型信息，就必须先获得正确的Class对象的引用。Class.forName()是实现此功能便捷的方式，因为你不需要为了获得Class引用而持有该类型的对象
 * 但是，如果你确定了一个对象，那么你可以使用getClass()获得Class引用，该方法属于Object的一部分，他将返回表示该对象的实际类型的Class引用。
 * Class包含了许多有用的方法，下面的例子是一部分： 见ToyTest.java
*/
