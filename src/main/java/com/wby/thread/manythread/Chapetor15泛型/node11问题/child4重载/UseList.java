package com.wby.thread.manythread.Chapetor15泛型.node11问题.child4重载;//: generics/UseList.java
// {CompileTimeError} (Won't compile)

import java.util.List;

/**
* @Description: 下面的程序是不能编译的，即使编译他是一种合理的尝试：
*/
public class UseList<W,T> {
  //void f(List<T> v) {}
  void f(List<W> v) {}
} ///:~
/**
* @Description: 由于擦除的原因，重载方法将产生相同的类型签名。
 *  与此不同的是，当贝擦除的参数不能产生唯一的参数列表时，必须提供明显有区别的方法名：
*/
class UseList2<W,T> {
  void f1(List<T> v) {}
  void f2(List<W> v) {}
} ///:~
/**
* @Description: 幸运的是，这类问题可以由编译器探测到
*/
