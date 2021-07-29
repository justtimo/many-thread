package com.wby.thread.manythread.Chaptor17容器深入研究.node4可选操作.child1未获支持的操作;//: containers/Unsupported.java
// Unsupported operations in Java containers.
import java.util.*;
/**
* @Description: 最常见的未获支持的操作，都来源于背后由固定尺寸的数据接口支持的容器。当你用Arrays.asList()将数组转换为List时，就会得到这样的容器。你
 * 还可以通过Collection类中“不可修改”的方法，选择创建任何会抛出UnsupportedOperationException的容器(包括Map)。
 *
 * 下面的例子包括这两种情况
*/
public class Unsupported {
  static void test(String msg, List<String> list) {
    System.out.println("--- " + msg + " ---");
    Collection<String> c = list;
    Collection<String> subList = list.subList(1,8);
    // Copy of the sublist:
    Collection<String> c2 = new ArrayList<String>(subList);
    try { c.retainAll(c2); } catch(Exception e) {
      System.out.println("retainAll(): " + e);
    }
    try { c.removeAll(c2); } catch(Exception e) {
      System.out.println("removeAll(): " + e);
    }
    try { c.clear(); } catch(Exception e) {
      System.out.println("clear(): " + e);
    }
    try { c.add("X"); } catch(Exception e) {
      System.out.println("add(): " + e);
    }
    try { c.addAll(c2); } catch(Exception e) {
      System.out.println("addAll(): " + e);
    }
    try { c.remove("C"); } catch(Exception e) {
      System.out.println("remove(): " + e);
    }
    // The List.set() method modifies the value but
    // doesn't change the size of the data structure:
    try {
      list.set(0, "X");
    } catch(Exception e) {
      System.out.println("List.set(): " + e);
    }
  }
  public static void main(String[] args) {
    List<String> list =
      Arrays.asList("A B C D E F G H I J K L".split(" "));
    test("Modifiable Copy", new ArrayList<String>(list));
    test("Arrays.asList()", list);
    test("unmodifiableList()",
      Collections.unmodifiableList(
        new ArrayList<String>(list)));
  }
} /* Output:
--- Modifiable Copy ---
--- Arrays.asList() ---
retainAll(): java.lang.UnsupportedOperationException
removeAll(): java.lang.UnsupportedOperationException
clear(): java.lang.UnsupportedOperationException
add(): java.lang.UnsupportedOperationException
addAll(): java.lang.UnsupportedOperationException
remove(): java.lang.UnsupportedOperationException
--- unmodifiableList() ---
retainAll(): java.lang.UnsupportedOperationException
removeAll(): java.lang.UnsupportedOperationException
clear(): java.lang.UnsupportedOperationException
add(): java.lang.UnsupportedOperationException
addAll(): java.lang.UnsupportedOperationException
remove(): java.lang.UnsupportedOperationException
List.set(): java.lang.UnsupportedOperationException
*///:~
/**
* @Description: 因为Arrays.asList()会生成一个List，它基于一个固定大小的数组，仅支持那些不会改变数组大小的操作，对他而言是有道理的。
 * 任何会引起对底层数据结构的尺寸进行修改的方法都会产生一个UnsupportedOperationException异常，以表示对未获支持操作的调用(一个编程错误)
 *
 * 注意，应该吧Arrays.asList()的结果作为构造器的参数传递给任何Collection(或者使用addAll()方法，或Collections.addAll()静态方法)，这样可以生成允许
 * 使用所有的方法的普通容器---这在main方法中的第一个对test()的调用中得到了展示，这样的调用会产生新的尺寸可调的底层数据结构。
 * Collections类中的“不可修改”的方法将容器包装到了一个代理中，只要你执行任何试图修改容器的操作，这个代理都会产生UnsupportedOperationException异常。
 * 使用这些方法的目标就是产生 常量 容器对象。“不可修改”的Collections方法的完整列表将在稍后介绍。
 *
 * test方法中的最后一个try语句块将检查作为List的一部分的set方法。这很有趣，因为你可以看到“未获支持的操作”这一技术的粒度来的是多么方便---所产生的的接口
 * 可以在Arrays.asList方法返回的对象和Collections.umodifiableList()返回的对象之间，在一个方法的力度上产生变化。
 * Arrays.asList返回固定尺寸的List，而Collections.unmodifiableList()产生不可修改的列表。
 * 正如所看到的，修改Arrays.asList返回的List中的元素是可以的，因为这没有违反List“尺寸固定”这一特性。
 * 但是很明显，unmodifiableList方法的结构在任何情况下都应该不是可修改的。如果使用该接口，那么还需要两个附加的接口，一个具有可以工作的set方法，另一个没有，
 * 因为附加的接口对于Collection的各种不可修改的子类型来说是必需的。
 *
 * 对于将容器作为参数接口的方法，其文档应该指定那些可选方法必须实现。
*/
