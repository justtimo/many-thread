//: net/mindview/util/Enums.java
package com.wby.thread.manythread.character19枚举类型.node6随机选取;
import java.util.*;

/**
 * 就像你在CartoonCharacter.next（）中看到的那样，本章中的很多示例都需要从enum实例中进行随机选择。我们可以利用泛型，从而使得这个工作更一般化，并将其加入到我们的工具库中。
 */
public class Enums {
  private static Random rand = new Random(47);
  public static <T extends Enum<T>> T random(Class<T> ec) {
    return random(ec.getEnumConstants());
  }
  public static <T> T random(T[] values) {
    return values[rand.nextInt(values.length)];
  }
} ///:~
/**
 * 古怪的语法<T extends Enum<T>>表示T是一个enum实例。而将Class<T>作为参数的话，我们就可以利用Class对象得到enum实例的数组了。重载后的random（）方法只需使用T【】作为参数，
 * 因为它并不会调用Enum上的任何操作，它只需从数组中随机选择一个元素即可。这样，最终的返回类型正是enum的类型。
 *
 * 下面是randomO方法的一个简单示例∶
 */
enum Activity { SITTING, LYING, STANDING, HOPPING,
  RUNNING, DODGING, JUMPING, FALLING, FLYING }

class RandomTest {
  public static void main(String[] args) {
    for(int i = 0; i < 20; i++)
      System.out.print(Enums.random(Activity.class) + " ");
  }
} /* Output:
STANDING FLYING RUNNING STANDING RUNNING STANDING LYING DODGING SITTING RUNNING HOPPING HOPPING HOPPING RUNNING STANDING LYING FALLING RUNNING FLYING LYING
*///:~
/**
 * 虽然Enum只是一个相当短小的类，但是在本章中你会发现，它能消除很多重复的代码。重复总会制造麻烦，因此消除重复总是有益处的。
 */
