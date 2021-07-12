package com.wby.thread.manythread.Chapetor15泛型.node8擦除的补偿;//: generics/Erased.java
// {CompileTimeError} (Won't compile)

/**
* @Description: 正如我们看到的，擦除丢失了在泛型代码中执行某些操作的能。任何在运行时需要知道确切类型的操作都将无法工作
*/
public class Erased<T> {
  private final int SIZE = 100;
  public static void f(Object arg) {
    //if(arg instanceof T) {}          // Error
    //T var = new T();                 // Error
    //T[] array = new T[SIZE];         // Error
    //T[] array = (T)new Object[SIZE]; // Unchecked warning
  }
} ///:~
/**
* @Description: 偶尔可以绕过这些问题来编程，但是有时必须通过引入类型标签来对擦除进行补偿，这意味着你需要显式的传递你的类型的Class对象，
 * 以便可以在类型表达式中使用它。
 *
 * 例如，前例中对使用instanceof的尝试最终失败了，因为其类型信息已经被擦除了。
 * 如果引入类型标签，就可以转而使用动态的isInstance():
*/
class Building {}
class House extends Building {}

class ClassTypeCapture<T> {
  Class<T> kind;
  public ClassTypeCapture(Class<T> kind) {
    this.kind = kind;
  }
  public boolean f(Object arg) {
    return kind.isInstance(arg);
  }
  public static void main(String[] args) {
    ClassTypeCapture<Building> ctt1 =
            new ClassTypeCapture<Building>(Building.class);
    System.out.println(ctt1.f(new Building()));
    System.out.println(ctt1.f(new House()));
    ClassTypeCapture<House> ctt2 =
            new ClassTypeCapture<House>(House.class);
    System.out.println(ctt2.f(new Building()));
    System.out.println(ctt2.f(new House()));
  }
} /* Output:
true
true
false
true
*///:~
/**
* @Description: 编译器将确保类型标签可以匹配泛型参数
*/
