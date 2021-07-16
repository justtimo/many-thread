package com.wby.thread.manythread.Chaptor16数组.node3返回一个数组;//: arrays/IceCream.java
// Returning arrays from methods.

import java.util.Arrays;
import java.util.Random;

/**
* @Description:  假设一个方法希望他返回的不止一个值，而是一组值。
 *  对于C或C++这有点困难，因为他们只能返回指向数组的指针。这会造成控制数组生命周期变得困难，并且容易造成内存泄漏。
 *  java中，你使用完后，垃圾回收期会清理掉它
 *
 *  下例展示如何返回Stirng数组
*/
public class IceCream {
  private static Random rand = new Random(47);
  static final String[] FLAVORS = {
    "Chocolate", "Strawberry", "Vanilla Fudge Swirl",
    "Mint Chip", "Mocha Almond Fudge", "Rum Raisin",
    "Praline Cream", "Mud Pie"
  };
  public static String[] flavorSet(int n) {
    if(n > FLAVORS.length)
      throw new IllegalArgumentException("Set too big");
    String[] results = new String[n];
    boolean[] picked = new boolean[FLAVORS.length];
    for(int i = 0; i < n; i++) {
      int t;
      do
        t = rand.nextInt(FLAVORS.length);
      while(picked[t]);
      results[i] = FLAVORS[t];
      picked[t] = true;
    }
    return results;
  }
  public static void main(String[] args) {
    for(int i = 0; i < 7; i++)
      System.out.println(Arrays.toString(flavorSet(3)));
  }
} /* Output:
[Rum Raisin, Mint Chip, Mocha Almond Fudge]
[Chocolate, Strawberry, Mocha Almond Fudge]
[Strawberry, Mint Chip, Mocha Almond Fudge]
[Rum Raisin, Vanilla Fudge Swirl, Mud Pie]
[Vanilla Fudge Swirl, Chocolate, Mocha Almond Fudge]
[Praline Cream, Strawberry, Mocha Almond Fudge]
[Mocha Almond Fudge, Strawberry, Mint Chip]
*///:~
/**
* @Description: 方法flavorSet创建了一个名为results的String数组。此数组容量为n，由传入方法的参数决定。然后从数组FLAVORS中随机
 * 选择元素，存入results数组中。返回一个数组与返回任何其他对象(实际上是返回引用)没什么区别。
 * 数组是在flavorSet中被创建还是在其他地方被创建，这不重要。当使用完毕，垃圾回收期负责清理；只要还需要他，此数组会一直存在。
 *
 * 注意当flavorSet随机选择各种数组元素“味道”时，他确保不会重复选择，由do循环不断进行随机选择，知道找出一个在数组picked中不存在的元素(当然。，还会比较Sring
 * 以检查随机选择的元素是否在数组results中)如果成功，将此元素加入数组，然后查找下一个(i递增)
 *
 * 从输出中可以看出，flavorSet每次确实是在随机选择味道
*/
