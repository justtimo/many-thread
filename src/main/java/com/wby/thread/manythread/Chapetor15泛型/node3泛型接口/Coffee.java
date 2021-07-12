//: generics/coffee/Coffee.java
package com.wby.thread.manythread.Chapetor15泛型.node3泛型接口;

/**
* @Description:  泛型可以应用于接口。例如 生成器 ，这是一种专门负责创建对象的类。
 *  实际上，这是 工厂方法设计模式 的一种应用。不过，当使用生成器创建新的对象时，它不需要任何参数，而工厂方法一般需要参数。也就是说，生成器无需额外的
 *  信息就知道如何创建新对象。
 *  一般而言，一个生成器只定一个方法，该方法用以产生新的对象。在这里，就是next()方法。
 *  interface Generator<T> {
 *   T next();
 * } ///:~
*/

/**
* @Description: 方法next()的返回类型是参数化的T。正如你所见到，接口使用泛型与类使用泛型没什么区别。
 *  为了延时如何实现Generator接口，我们还需要一些别的类。例如，Coffee类层次结构如下：
*/
public class Coffee {
  private static long counter = 0;
  private final long id = counter++;
  public String toString() {
    return getClass().getSimpleName() + " " + id;
  }
} ///:~

class Latte extends Coffee {} ///:~

class Mocha extends Coffee {} ///:~

class Cappuccino extends Coffee {} ///:~

class Americano extends Coffee {} ///:~

class Breve extends Coffee {} ///:~
/**
* @Description:  现在我们可以编写一个类，实现Generator<Coffee>接口，他能够随机生成不同类型的Coffee对象：
 * 见CoffeeGenerator.java
*/

/**

*/


























