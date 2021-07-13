package com.wby.thread.manythread.Chapetor15泛型.node10通配符.child4捕获转换;//: generics/CaptureConversion.java

import com.wby.thread.manythread.Chapetor15泛型.node10通配符.child1编译器有多聪明.Holder;
/**
* @Description:  有一种情况特别需要使用<?>而不是原生类型。如果像一个使用<?>的方法传递原生类型，那么对编译器来说，可能会推断出实际的类型参数，使得方法
 * 可以回转并调用另一个使用这个确切类型的方法，可能会推断出实际的类型参数，使得这个方法可以回转并调用另一个使用这个确切类型的方法。
 * 下面的示例演示了这种计数，他被称为 捕获转换，因为未指定的通配符类型被捕获，并被转换为确切类型。这里，有关警告的注释只有在@SuppressWarnings("unchecked")
 * 注解被移除之后才能起作用：
*/
public class CaptureConversion {
  static <T> void f1(Holder<T> holder) {
    T t = holder.get();
    System.out.println(t.getClass().getSimpleName());
  }
  static void f2(Holder<?> holder) {
    f1(holder); // Call with captured type
  }
  @SuppressWarnings("unchecked")
  public static void main(String[] args) {
    Holder raw = new Holder<Integer>(1);
    // f1(raw); // Produces warnings
    f2(raw); // No warnings
    Holder rawBasic = new Holder();
    rawBasic.set(new Object()); // Warning
    f2(rawBasic); // No warnings
    // Upcast to Holder<?>, still figures it out:
    Holder<?> wildcarded = new Holder<Double>(1.0);
    f2(wildcarded);
  }
} /* Output:
Integer
Object
Double
*///:~
/**
* @Description: f1中的类型参数都是确切的，没有通配符或边界。
 * f2中，Holder参数是一个无界通配符，因此他看起来是未知的。但是f2中，f1被调用，而f1需要一个已知参数。这里所发生的是：参数类型在调用f2的过程中被
 * 捕获，因此他可以在对f1的调用中使用。
 *
 * 你可能想知道，这种技术是否可以用于写入，但是这要求要在传递Holder<?>时同时传递一个具体类型。
 * 捕获转换只有在这样的情况下可以工作：即方法内部，你需要使用确切的类型。
 * 注意，不能从f2中返回T，因为T对于f2来说是未知的。捕获转换十分有趣，但是非常受限
*/
