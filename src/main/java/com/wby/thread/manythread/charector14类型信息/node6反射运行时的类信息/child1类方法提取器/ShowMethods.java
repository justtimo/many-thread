package com.wby.thread.manythread.charector14类型信息.node6反射运行时的类信息.child1类方法提取器;//: typeinfo/ShowMethods.java
// Using reflection to show all the methods of a class,
// even if the methods are defined in the base class.
// {Args: ShowMethods}

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.regex.Pattern;

import static com.wby.thread.manythread.net.mindview.util.Print.print;


/**
 * @Description:  通常不需要直接使用反射工具，但他们在你需要更动态的代码时会很有用。
 *  反射在java中是用来支持其他特性的，比如对象序列化和javaBean。
 *
 *  但是能动态的获取某个类的信息也是很有用的。
 *  请考虑类方法提取器。
 *  浏览实现了类定义的源代码或是其JDK文档，只能找到 在这个类定义中 被定义或被覆盖的方法。但是，可能有数十个更有用的方法都是继承自基类的。
 *  要找出这些方法乏味且费时，但是反射提供了一种方法，使我们呢能够编写自动展示完整接口的简单工具，见下例：
 */
public class ShowMethods {
  private static String usage =
    "usage:\n" +
    "ShowMethods qualified.class.name\n" +
    "To show all methods in class or:\n" +
    "ShowMethods qualified.class.name word\n" +
    "To search for methods involving 'word'";
  private static Pattern p = Pattern.compile("\\w+\\.");
  public static void main(String[] args) {
    if(args.length < 1) {
      print(usage);
      System.exit(0);
    }
    int lines = 0;
    try {
      Class<?> c = Class.forName(args[0]);
      Method[] methods = c.getMethods();
      Constructor[] ctors = c.getConstructors();
      if(args.length == 1) {
        for(Method method : methods)
          print(
            p.matcher(method.toString()).replaceAll(""));
        for(Constructor ctor : ctors)
          print(p.matcher(ctor.toString()).replaceAll(""));
        lines = methods.length + ctors.length;
      } else {
        for(Method method : methods)
          if(method.toString().indexOf(args[1]) != -1) {
            print(
              p.matcher(method.toString()).replaceAll(""));
            lines++;
          }
        for(Constructor ctor : ctors)
          if(ctor.toString().indexOf(args[1]) != -1) {
            print(p.matcher(
              ctor.toString()).replaceAll(""));
            lines++;
          }
      }
    } catch(ClassNotFoundException e) {
      print("No such class: " + e);
    }
  }
} /* Output:
public static void main(String[])
public native int hashCode()
public final native Class getClass()
public final void wait(long,int) throws InterruptedException
public final void wait() throws InterruptedException
public final native void wait(long) throws InterruptedException
public boolean equals(Object)
public String toString()
public final native void notify()
public final native void notifyAll()
public ShowMethods()
*///:~
/**
* @Description: Class的getMethods()和getConstructor()方法分别返回Method对象的数组和Constructor对象的数组。
 *  这两个类都提供了深沉次的方法，用以解析其对象所代表的的方法，并获取其名字、输入参数、返回值。
 *  但是也可以像例子中一样，只是用toString()生成一个含有完整的方法特征签名的字符串。代码其他部分用于提取命令行信息，判断某个特定的特征签名是否与我们的目标字符串相符(使用indexOf())
 *  并使用正则表达式去掉了命名修饰词。
 *
 *  Class.forName()生成的结果在编译时是不可知的，因此所有的方法特征签名信息都是在执行时被提取出来的。
 *  反射提供了足够的支持，使得呢能够创建一个在编译时完全未知的对象，并调用此对象的方法。
 *  上面的输出是从下面的命令行产生的：
 *    java ShowMethods ShowMethods
 *  可以看到，输出中包含一个public的默认构造器，即使能在代码中看到根本没有定义任何构造器。
 *  所看到的这个包含在列表中的构造器是自动合成的。如果将ShowMethods作为一个非public的类(也就是拥有包访问权限)，输出中就不会再显示出这个自动合成的默认构造器。
 *  该自动合成的默认构造器会自动的被赋予与类一样的访问权限、
 *
 *  还有一个有趣的例子，用一个额外的char、int或String等参数来调用java ShowMethods java.lang.String
 *
 *  在编程时，特别是如果不记得一个类是否有某个方法，或者不知道这个类能做什么，例如Color对象，而又不想通过索引或类的层次结构去查看JDK文档，这个工具确实能洁身很多时间
*/
