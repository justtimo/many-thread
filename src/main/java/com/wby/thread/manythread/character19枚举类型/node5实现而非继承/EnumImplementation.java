//: enumerated/cartoons/EnumImplementation.java
// An enum can implement an interface
package com.wby.thread.manythread.character19枚举类型.node5实现而非继承;

import com.wby.thread.manythread.net.mindview.util.Generator;

import java.util.Random;

/**
 * 我们已经知道，所有的enum都继承自java.lang.Enum类。由于Java不支持多重继承，所以你的enum不能再继承其他类∶
 * enum NotPossible extends Pet { ...// Won't work
 * 然而，在我们创建一个新的enum时，可以同时实现一个或多个接口∶
 */
enum CartoonCharacter
implements Generator<CartoonCharacter> {
  SLAPPY, SPANKY, PUNCHY, SILLY, BOUNCY, NUTTY, BOB;
  private Random rand = new Random(47);
  public CartoonCharacter next() {
    return values()[rand.nextInt(values().length)];
  }
}

public class EnumImplementation {
  public static <T> void printNext(Generator<T> rg) {
    System.out.print(rg.next() + ", ");
  }
  public static void main(String[] args) {
    // Choose any instance:
    CartoonCharacter cc = CartoonCharacter.BOB;
    for(int i = 0; i < 10; i++)
      printNext(cc);
  }
} /* Output:
BOB, PUNCHY, BOB, SPANKY, NUTTY, PUNCHY, SLAPPY, NUTTY, NUTTY, SLAPPY,
*///:~
/**
 * 这个结果有点奇怪，不过你必须要有一个enum实例才能调用其上的方法。现在，在任何接受 Generator参数的方法中，例如printNextO，都可以使用CartoonCharacter。
 */
