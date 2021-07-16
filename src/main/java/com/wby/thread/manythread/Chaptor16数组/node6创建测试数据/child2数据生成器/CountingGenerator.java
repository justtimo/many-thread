//: net/mindview/util/CountingGenerator.java
// Simple generator implementations.
package com.wby.thread.manythread.Chaptor16数组.node6创建测试数据.child2数据生成器;

import com.wby.thread.manythread.net.mindview.util.Generator;

import java.util.Random;

/**
* @Description:  为了灵活的方式创建更有意义的数组，我们使用15章引入的Generator概念。
 * 如果某个工具使用了Generator，那么就可以通过选择Generator的类型来创建任何类型的数据(这是策略设计模式的例子---每个不同的Generator都标识一个不同的策略)
 *
 * 首先给出的是可以用于所有基本类型的包装器类型，以及String类型的最基本的计数生成器集合。这些生成器将嵌套在CountingGenerator中，从而
 * 使得他们能够使用与所要生成的对象类型相同的名字。
 * 例如，创建Integer对象的生成器可以通过表达式new CountingGenerator.Integer()来创建：
*/
public class CountingGenerator {
  public static class
  Boolean implements Generator<java.lang.Boolean> {
    private boolean value = false;
    public java.lang.Boolean next() {
      value = !value; // Just flips back and forth
      return value;
    }
  }
  public static class
  Byte implements Generator<java.lang.Byte> {
    private byte value = 0;
    public java.lang.Byte next() { return value++; }
  }
  static char[] chars = ("abcdefghijklmnopqrstuvwxyz" +
    "ABCDEFGHIJKLMNOPQRSTUVWXYZ").toCharArray();
  public static class
  Character implements Generator<java.lang.Character> {
    int index = -1;
    public java.lang.Character next() {
      index = (index + 1) % chars.length;
      return chars[index];
    }
  }
  public static class
  String implements Generator<java.lang.String> {
    private int length = 7;
    Generator<java.lang.Character> cg = new Character();
    public String() {}
    public String(int length) { this.length = length; }
    public java.lang.String next() {
      char[] buf = new char[length];
      for(int i = 0; i < length; i++)
        buf[i] = cg.next();
      return new java.lang.String(buf);
    }
  }
  public static class
  Short implements Generator<java.lang.Short> {
    private short value = 0;
    public java.lang.Short next() { return value++; }
  }
  public static class
  Integer implements Generator<java.lang.Integer> {
    private int value = 0;
    public java.lang.Integer next() { return value++; }
  }
  public static class
  Long implements Generator<java.lang.Long> {
    private long value = 0;
    public java.lang.Long next() { return value++; }
  }
  public static class
  Float implements Generator<java.lang.Float> {
    private float value = 0;
    public java.lang.Float next() {
      float result = value;
      value += 1.0;
      return result;
    }
  }
  public static class
  Double implements Generator<java.lang.Double> {
    private double value = 0.0;
    public java.lang.Double next() {
      double result = value;
      value += 1.0;
      return result;
    }
  }
} ///:~
/**
* @Description:  上面的每个类都实现了某种意义的“计数”。在CountingGenerator中，计数只是不断地重复大小写字母；
 * CountingGenerator.String类使用CountingGenerator.Charactor类填充一个字符数组，该数组被转换为String，数组的尺寸取决于构造器参数。
 *
 * 请注意，CountingGenerator.String使用的是基本的Generator<Character>而不是具体的对CountingGenerator.Character的引用。
 * 稍后，我们可以替换这个生成器，以生成RandomGenerator中的RandomGenerator.String
 *
 * 下面是一个测试工具，针对嵌套的Generator这一惯用法，因为使用了反射所以这个工具可以遵循下面的形式来测试Generator的任何集合
*/
class GeneratorsTest {
  public static int size = 10;
  public static void test(Class<?> surroundingClass) {
    for(Class<?> type : surroundingClass.getClasses()) {
      System.out.print(type.getSimpleName() + ": ");
      try {
        Generator<?> g = (Generator<?>)type.newInstance();
        for(int i = 0; i < size; i++)
          System.out.printf(g.next() + " ");
        System.out.println();
      } catch(Exception e) {
        throw new RuntimeException(e);
      }
    }
  }
  public static void main(String[] args) {
    test(CountingGenerator.class);
  }
} /* Output:
Double: 0.0 1.0 2.0 3.0 4.0 5.0 6.0 7.0 8.0 9.0
Float: 0.0 1.0 2.0 3.0 4.0 5.0 6.0 7.0 8.0 9.0
Long: 0 1 2 3 4 5 6 7 8 9
Integer: 0 1 2 3 4 5 6 7 8 9
Short: 0 1 2 3 4 5 6 7 8 9
String: abcdefg hijklmn opqrstu vwxyzAB CDEFGHI JKLMNOP QRSTUVW XYZabcd efghijk lmnopqr
Character: a b c d e f g h i j
Byte: 0 1 2 3 4 5 6 7 8 9
Boolean: true false true false true false true false true false
*///:~
/**
* @Description: 这里假设待测类包含一组嵌套的Generator对象，其中每个都有一个默认构造器。
 * 反射方法getClasses可以生成所有的嵌套类，而test方法可以为这些生成器中的吗，每一个都创建一个实例，然后打印通过调用10次next方法而产生效果
 *
 * 下面一组是使用随机数生成器的Generator。因为Random构造器使用常量进行初始化，所以每次调用这些Generator中的一个来运行程序是，所产生的的输出都是可重复的
*/
class RandomGenerator {
  private static Random r = new Random(47);
  public static class
  Boolean implements Generator<java.lang.Boolean> {
    public java.lang.Boolean next() {
      return r.nextBoolean();
    }
  }
  public static class
  Byte implements Generator<java.lang.Byte> {
    public java.lang.Byte next() {
      return (byte)r.nextInt();
    }
  }
  public static class
  Character implements Generator<java.lang.Character> {
    public java.lang.Character next() {
      return CountingGenerator.chars[
              r.nextInt(CountingGenerator.chars.length)];
    }
  }
  public static class
  String extends CountingGenerator.String {
    // Plug in the random Character generator:
    { cg = new Character(); } // Instance initializer
    public String() {}
    public String(int length) { super(length); }
  }
  public static class
  Short implements Generator<java.lang.Short> {
    public java.lang.Short next() {
      return (short)r.nextInt();
    }
  }
  public static class
  Integer implements Generator<java.lang.Integer> {
    private int mod = 10000;
    public Integer() {}
    public Integer(int modulo) { mod = modulo; }
    public java.lang.Integer next() {
      return r.nextInt(mod);
    }
  }
  public static class
  Long implements Generator<java.lang.Long> {
    private int mod = 10000;
    public Long() {}
    public Long(int modulo) { mod = modulo; }
    public java.lang.Long next() {
      return new java.lang.Long(r.nextInt(mod));
    }
  }
  public static class
  Float implements Generator<java.lang.Float> {
    public java.lang.Float next() {
      // Trim all but the first two decimal places:
      int trimmed = Math.round(r.nextFloat() * 100);
      return ((float)trimmed) / 100;
    }
  }
  public static class
  Double implements Generator<java.lang.Double> {
    public java.lang.Double next() {
      long trimmed = Math.round(r.nextDouble() * 100);
      return ((double)trimmed) / 100;
    }
  }
} ///:~
/**
* @Description: 可以看到，RandomGenerator。String继承自CountingGenerator。String，并且只是插入了新的Character生成器。
 *
 * 为了不生成过大的数字，RandomGenerator。Integer默认使用的模数是10000，但是重载的构造器允许你选择更小的值。
 * 同样的方式也应用到了RandomGnerator。Long上。
 * 对于Float和Double生成器，小数点之后的数字被截掉了
 *
 * 我们复用GeneratorTest来测试RandomGernerator：
*/
class RandomGeneratorsTest {
  public static void main(String[] args) {
    GeneratorsTest.test(RandomGenerator.class);
  }
} /* Output:
Double: 0.73 0.53 0.16 0.19 0.52 0.27 0.26 0.05 0.8 0.76
Float: 0.53 0.16 0.53 0.4 0.49 0.25 0.8 0.11 0.02 0.8
Long: 7674 8804 8950 7826 4322 896 8033 2984 2344 5810
Integer: 8303 3141 7138 6012 9966 8689 7185 6992 5746 3976
Short: 3358 20592 284 26791 12834 -8092 13656 29324 -1423 5327
String: bkInaMe sbtWHkj UrUkZPg wsqPzDy CyRFJQA HxxHvHq XumcXZJ oogoYWM NvqeuTp nXsgqia
Character: x x E A J J m z M s
Byte: -60 -17 55 -14 -5 115 39 -37 79 115
Boolean: false true false false true true true true true true
*///:~
/**
* @Description: 你可以通过修改public的GeneratorTest。size的值，来改变所产生的的数值数量。
*/
