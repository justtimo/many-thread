package com.wby.thread.manythread.charector14类型信息.node2Class.child1类字面量;//: typeinfo/ClassInitialization.java

import java.util.Random;

/**
 * @Description:  java还提供了另外一种方式生成对Class对象的引用，即 类字面量，使用方式：
 *  FancyToy.class
 *  这样更简单，更安全、更高效；因为他在编译器就会受到检查，并且他根除了对forName()的调用，所以更高效
 *
 *  类字面量可以用于普通类、接口、数组以及基本数据类型。
 *  另外对于基本数据类型的包装器类，还有一个标准字段TYPE。TYPE字段是一个引用，指向对应的基本数据类型的Class对象
 *  作者建议使用.class的形式，以保持与普通类的一致性
 *
 *  注意：当使用“.class”来创建对Class对象的引用时，不会自动的初始化该Class对象。
 *  为了使用类而做的准备工作实际上包含三个步骤：
 *    1.加载  由类加载器执行。该步骤将查找字节码(通常在classPath所指定的路径中查找，但这是非必需的)，并从字节码中创建一个Class对象
 *    2.链接  在链接阶段将验证类中的字节码，为静态域分配存储空间，如果必需的话，将解析这个类创建的对其他类的所有引用。
 *    3.初始化 如果该类具有超类，则对其初始化，执行静态初始化器和静态初始化块
 *  初始化被延迟到了对静态方法(构造器隐式的是静态的)或者非常数静态域进行首次引用是才执行：
 */
class Initable {
  static final int staticFinal = 47;
  static final int staticFinal2 =
    ClassInitialization.rand.nextInt(1000);
  static {
    System.out.println("Initializing Initable");
  }
}

class Initable2 {
  static int staticNonFinal = 147;
  static {
    System.out.println("Initializing Initable2");
  }
}

class Initable3 {
  static int staticNonFinal = 74;
  static {
    System.out.println("Initializing Initable3");
  }
}
public class ClassInitialization {
  public static Random rand = new Random(47);
  public static void main(String[] args) throws Exception {
    Class initable = Initable.class;
    System.out.println("After creating Initable ref");
    // Does not trigger initialization:
    System.out.println(Initable.staticFinal);
    // Does trigger initialization:
    System.out.println(Initable.staticFinal2);
    // Does trigger initialization:
    System.out.println(Initable2.staticNonFinal);
    Class initable3 = Class.forName("com.wby.thread.manythread.charector14类型信息.node2Class.child1类字面量.Initable3");
    System.out.println("After creating Initable3 ref");
    System.out.println(Initable3.staticNonFinal);
  }
} /* Output:
After creating Initable ref
47
Initializing Initable
258
Initializing Initable2
147
Initializing Initable3
After creating Initable3 ref
74
*///:~
/**
* @Description: 初始化有效的实现了尽可能的“惰性”。
 *  从对initable引用的创建中可以看到，仅使用.class语法来获得对类的引用不会引发初始化。
 *  但是，为了产生class引用，Class.forName()立即进行了初始化，就像在对initable3引用的创建中所看到的
 *
 *  如果一个static final值是“编译器常量”，就像Initable.staticFinal那样，那么这个值不需要对Initable类进行初始化就可以被读取。
 *  但是如果将一个域设置为static final的，还不足以确保这种行为，例如对Initable.staticFinal2的访问将强制进行类的初始化，因为他不是一个编译器常量
 *
 *  如果一个static域不是final的，那么对他访问时，总是要求在它被读取之前，要先进行链接(为这个域分配存储空间)和初始化(初始化该存储空间)，就像在Initable2.staticNonFinal
 *  的访问中所看到的那样
*/
