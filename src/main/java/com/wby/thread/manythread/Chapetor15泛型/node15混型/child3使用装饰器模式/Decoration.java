//: generics/decorator/Decoration.java
package com.wby.thread.manythread.Chapetor15泛型.node15混型.child3使用装饰器模式;

import java.util.Date;

/**
* @Description: 当你观察混型的使用方式时，就会发现混型概念好像与 装饰器 设计模式关系很近。
 *  装饰器经常用于满足各种可能的组合，而直接子类化会产生过多的类，因此是不实际的。
 *
 *  装饰器模式使用分层对象来动态透明的向每个对象中添加责任。装饰器指定包装在最初的对象周围的所有对象都具有相同的基本接口。
 *  某些事物是可以装饰的，可以通过将其他类包装在这个可装饰对象的四周，来将功能封层。这使得对装饰器的使用是透明的---无论对象是否被装饰，
 *  你都拥有一个可以向对象发送的公共消息集。装饰类也可以添加新方法，但是正如你所见，这是受限的。
 *
 *  装饰器是通过使用组合和形式化结构(可装饰物、装饰器层次结构)来实现的，而混型是基于继承的。
 *  因此，可以将基于参数化类型的混型当做是一种泛型装饰器机制，这种机制不需要装饰器设计模式的继承结构。
 *
 *  前面的例子可以被改写为使用装饰器：
*/
class Basic {
  private String value;
  public void set(String val) { value = val; }
  public String get() { return value; }
}

class Decorator extends Basic {
  protected Basic basic;
  public Decorator(Basic basic) { this.basic = basic; }
  public void set(String val) { basic.set(val); }
  public String get() { return basic.get(); }
}

class TimeStamped extends Decorator {
  private final long timeStamp;
  public TimeStamped(Basic basic) {
    super(basic);
    timeStamp = new Date().getTime();
  }
  public long getStamp() { return timeStamp; }
}

class SerialNumbered extends Decorator {
  private static long counter = 1;
  private final long serialNumber = counter++;
  public SerialNumbered(Basic basic) { super(basic); }
  public long getSerialNumber() { return serialNumber; }
}

public class Decoration {
  public static void main(String[] args) {
    TimeStamped t = new TimeStamped(new Basic());
    TimeStamped t2 = new TimeStamped(
      new SerialNumbered(new Basic()));
    //! t2.getSerialNumber(); // Not available
    SerialNumbered s = new SerialNumbered(new Basic());
    SerialNumbered s2 = new SerialNumbered(
      new TimeStamped(new Basic()));
    //! s2.getStamp(); // Not available
  }
} ///:~
/**
* @Description: 产生自泛型的类包含所有感兴趣的方法，但是由使用装饰器所产生的的对象类型是最后被装饰的类型。
 * 即，尽管可以添加多个层，但是最后一层才是实际的类型，因此只有最后一层的方法是可视的，而混型的类型是所有被混合到一起的类型。
 * 因此对于装饰起来说，其明显的缺陷是他只能有效的工作与装饰中的一层(最后一层),而混型方法显然会更自然一些。
 * 因此，装饰器只是对由混型提出的问题的一种局限的解决方案。
*/
