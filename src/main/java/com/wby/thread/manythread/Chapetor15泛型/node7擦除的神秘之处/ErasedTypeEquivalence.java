package com.wby.thread.manythread.Chapetor15泛型.node7擦除的神秘之处;//: generics/ErasedTypeEquivalence.java

import java.util.*;

/**
* @Description: 当你开始深入钻研泛型时，会发现有大量的东西初看起来是没有意义的。
 *  例如，尽管可以声明ArrayList.class，但是不能声明ArrayList<Integer>.class。请考虑下面的情况：
*/
public class ErasedTypeEquivalence {
  public static void main(String[] args) {
    Class c1 = new ArrayList<String>().getClass();
    Class c2 = new ArrayList<Integer>().getClass();
    System.out.println(c1 == c2);
  }
} /* Output:
true
*///:~
/**
* @Description: ArrayList<String>和 ArrayList<Integer>很容易被认为是不同的类型。不同的类型在行为方面肯定不同，例如，如果尝试着
 *  讲一个Integer放入ArrayList<String>，所得到的行为(将失败)与把一个Integer放入ArrayList<Integer>(将成功)所得到的行为完全不同。
 *  但是上面的程序会认为他们是相同的类型。
 *
 *  下面的示例是对这个迷题的补充：
*/
class Frob {}
class Fnorkle {}
class Quark<Q> {}
class Particle<POSITION,MOMENTUM> {}

class LostInformation {
  public static void main(String[] args) {
    List<Frob> list = new ArrayList<Frob>();
    Map<Frob,Fnorkle> map = new HashMap<Frob,Fnorkle>();
    Quark<Fnorkle> quark = new Quark<Fnorkle>();
    Particle<Long,Double> p = new Particle<Long,Double>();
    System.out.println(Arrays.toString(
            list.getClass().getTypeParameters()));
    System.out.println(Arrays.toString(
            map.getClass().getTypeParameters()));
    System.out.println(Arrays.toString(
            quark.getClass().getTypeParameters()));
    System.out.println(Arrays.toString(
            p.getClass().getTypeParameters()));
  }
} /* Output:
[E]
[K, V]
[Q]
[POSITION, MOMENTUM]
*///:~
/**
* @Description:  根据JDK文档的描述，Class.getTypeParameters()将“返回一个TypeVariable对象数组，表示有泛型声明所声明的类型参数。。。”
 *  这好像是在暗示你可能发现参数类型的信息，但是，正如你所看到的，你能够发现的只是用作参数占位符的标识符，这并非有用的信息。
 *
 *  因此，残酷的事实是：
 *    在泛型代码内部，无法获得任何有关泛型参数类型的信息。
 *  因此，你可以知道注入类型参数标识符和泛型类型边界这类的信息----你却无法知道用来创建某个特定实例的实际的类型参数。
 *  在使用java泛型工作时他是必须处理的最基本的问题
 *
 *  java 泛型时使用擦除来实现的，这意味着当你使用泛型时，任何具体的类型信息都被擦除了，你唯一知道的就是你在使用一个对象。因此
 *  ArrayList<String>和 ArrayList<Integer>在运行时事实上是相同的类型。这两种形式都被擦除成他们的“原生”类型，即List。
 *  理解擦除以及应该如何处理它，是你在学习java泛型时面临的最大障碍，这也是我们在本节将要探讨的内容。
*/
